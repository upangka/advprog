"""
================================================================================
模块说明：数据预处理与格式化脚本
================================================================================
本模块负责将原始数据进行清洗、分词、编码与划分，最终生成模型可直接读取的
标准格式数据集，并保存到 jsonl 文件中。

--------------------------------------------------------------------------------
处理流程图：
--------------------------------------------------------------------------------

[ 原始数据 (Raw Data) ]                         [ 训练数据集 (Train Set) ]
+-----------------------------+                 +--------------------------------------+
| {                           |                 | {"input": [625, 103, ..., 808],      |
|   "topic": "校园生活分享",    |                 |  "target": 597}                      |
|   "user1": "李欣怡",         |                 | {"input": [103, 932, ..., 597],      |
|   "user2": "杨欢",           |                 |  "target": 13}                       |
|   "dialog": [               |                 | ... (省略部分数据)                    |
|     {                       |                 +--------------------------------------+
|       "user1": "杨欢，...？", |                                 ^
|       "user2": "嗨，李欣怡！" |                                 |
|     },                      |                 ==================================
|     ... (多轮对话)           | =============>  | 数据清洗 / 分词 / 编码 / 划分 |
|   ]                         | =============>  ==================================
| }                           |                                 |
+-----------------------------+                                 v
                                                +--------------------------------------+
                                                | [ 测试数据集 (Test Set) ]             |
                                                +--------------------------------------+
                                                | {"input": [637, 59, ..., 638, 3],    |
                                                |  "target": 4}                        |
                                                | {"input": [59, 274, ..., 4, 30],     |
                                                |  "target": 30}                       |
                                                | ... (省略部分数据)                    |
                                                +--------------------------------------+

--------------------------------------------------------------------------------
流程步骤说明：
--------------------------------------------------------------------------------
1. 读取 raw 目录下的原始 JSON 数据（包含 topic, user, 多轮 dialog）。
2. 对文本进行清洗与分词（Tokenizer）。
3. 将 Token 转化为模型可读的 ID 序列（如: 625, 103...）。
4. 划分数据集为 Train / Test，并以 JSONL (JSON Lines) 格式保存。
   注：每行是一个独立的 JSON 对象，包含 "input" (输入序列) 和 "target" (目标ID)。
"""
import jieba
import pandas as pd
from sklearn.model_selection import train_test_split
from tqdm import tqdm

from config import RAW_DATA_DIR,MODELS_DIR,SEQ_LEN,PROCESSED_DATA_DIR


def build_dataset(sentences, word2Index,desc):
    indexed_sentences = [[word2Index.get(token,0) for token in jieba.lcut(sentence)] for sentence in sentences]
        
    dataset = []
    for sentence in tqdm(indexed_sentences,desc=desc):
        for i in range(len(sentence) - SEQ_LEN):
            input = sentence[i:i + SEQ_LEN]
            target = sentence[i + SEQ_LEN]
            dataset.append({
                'input': input,
                'target': target
            })
    return dataset

def process():
    
    # 1. 读取数据
    # df = pd.read_json(RAW_DATA_DIR / "synthesized_.jsonl",orient="records",lines=True).sample(frac=0.1)
    df = pd.read_json(RAW_DATA_DIR / "synthesized_.jsonl",orient="records",lines=True)
    
    # 2. 提取句子
    sentences = [] 
    for dialog in df['dialog']:
        for sentence in dialog:
            sentences.append(sentence.split("：")[1])
    
    print(sentences[:3])
    print(f"{len(sentences)}")

    # 3. 划分数据集
    train_sentences, test_sentences = train_test_split(sentences,test_size=0.2)
    print(f'{len(train_sentences)} + {len(test_sentences)}')
    
    # 4. 构建词汇表
    vocab_set = set()
    for sentence in tqdm(train_sentences,desc="构建词表vocabs"):
        vocab_set.update(jieba.lcut(sentence))
        
    vocabs = ['<unk>'] + sorted(list(vocab_set))
    
    with open(PROCESSED_DATA_DIR / "vocabs.txt",  mode="w",encoding="utf-8") as f:
        f.write("\n".join(vocabs))
    
    print(f'词表构建完成，一共{len(vocabs)}')
    
    
    # 5. 构建训练集
    word2Index = {word: index for index,word in enumerate(vocabs)}
    train_dataset = build_dataset(train_sentences,word2Index,desc="构建训练数据集")
    print(train_dataset[0:3])   
    # 6. 保存训练集
    pd.DataFrame(train_dataset).to_json(PROCESSED_DATA_DIR / "train.jsonl",orient='records',lines=True)   
    
    # 7. 构建测试集
    test_dataset = build_dataset(test_sentences,word2Index,desc="构建测试数据集")
    print(test_dataset[0:3])   
    # 8. 保存测试集
    pd.DataFrame(test_dataset).to_json(PROCESSED_DATA_DIR / "test.jsonl",orient='records',lines=True)       
    
    
    

if __name__ == "__main__":
    process()