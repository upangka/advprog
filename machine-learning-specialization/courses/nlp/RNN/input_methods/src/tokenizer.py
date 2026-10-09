import jieba
from tqdm import tqdm

import config

class JiebaTokenizer:
    unk_token = '<unk>'
    
    def __init__(self,vocab_list):
        """
        vocab_list: 词汇列表
        """
        self.vocab_list = vocab_list
        self.vocab_size = len(self.vocab_list)
        self.word2Index = {word: index for index,word in enumerate(self.vocab_list)}
        self.index2Word = {index: word for index,word in enumerate(self.vocab_list)}
        self.unk_token_index = self.word2Index.get(self.unk_token)
        
        
    @staticmethod
    def tokenize(text):
        """
        输入的一段话进行分词
        """
        return jieba.lcut(text)
    
    
    def encode(self,text):
        """
        对一段话进行分词，并映射到对应的index
        """
        tokens = self.tokenize(text)
        return [self.word2Index.get(token,self.unk_token_index) for token in tokens]
        
    @classmethod
    def build_vocab(cls,sentences,path):
        """构建词汇表
        sentences: 训练数据
        path: 保存预料
        """
        vocab_set = set()
        for sentence in tqdm(sentences,desc="构建词汇表"):
            vocab_set.update([word.strip() for word in jieba.lcut(sentence)])
        
        vocab_list = [cls.unk_token] + list(vocab_set)
        print(f"词汇表大小: {len(vocab_list)}")
        
        # 保存词汇表
        with open(path,mode='w',encoding="utf-8") as f:
            f.write('\n'.join(vocab_list))
        
    @classmethod
    def from_vocab(cls,path):
        """
        path: 词汇表文件路径
        """
        with open(path,encoding="utf-8") as f:
            vocab_list = [word.strip() for word in f]
        return cls(vocab_list)
        
        
if __name__ == '__main__':
    tokenizer = JiebaTokenizer.from_vocab(config.PROCESSED_DATA_DIR / "vocabs.txt")
    print(f'词表大小：{tokenizer.vocab_size}')
    print(f'特殊符号：{tokenizer.unk_token}')
    print(tokenizer.encode("今天天气不错"))