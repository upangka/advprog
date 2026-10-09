"""
1. Loss 是“信号”，它通过 backward() 把梯度存到参数的 .grad 里。
2. Optimizer 是“执行者”，它通过 step() 读取 .grad 并改参数的值。
3. 它们通过模型参数本身这个共享内存联系在了一起。

标准三步曲：清零 -> 反向 -> 更新（zero_grad -> backward -> step）
"""
import time
import torch
from torch.utils.tensorboard import SummaryWriter
from tqdm import tqdm

import config
from dataset import get_dataloader
from tokenizer import JiebaTokenizer
from model import InputMethodModel

def train_one_epoch(model, dataloader, loss_fn, optimizer, device,epoch):
    """
    训练一个轮次
    :param model: 模型
    :param dataloader: 数据集
    :param loss_fn: 损失函数
    :param optimizer: 优化器
    :param device: 设备
    :return: 当前epoch的平均loss
    """
    # 切换训练模式
    model.train()
    total_loss,total_samples = 0,0
    for inputs,targets in tqdm(dataloader, desc=f"训练 Epoch {epoch}"):
        # inputs.shape: [batch_size, seq_len]
        inputs = inputs.to(device)
        # targets.shape: [batch_size]
        targets = targets.to(device)
        
        # 向前传播
        output = model(inputs)
        # output.shape: [batch_size,vocab_size]
        
        # 清零 -> 反向 -> 更新（zero_grad -> backward -> step）
        loss = loss_fn(output,targets)        
        optimizer.zero_grad()
        loss.backward()
        optimizer.step()
        
        total_loss += loss.item() * inputs.size(0)   # 累加"该 batch 的总损失"
        total_samples += inputs.size(0)              # 累加"该 batch 的样本数"
    return total_loss / total_samples
    
    
    
def train():
    # 1. 确定设备
    device = torch.device('cuda' if torch.cuda.is_available() else "cpu")

    # 2. 数据集
    dataloader = get_dataloader()

    # 3.词表
    tokenizer = JiebaTokenizer.from_vocab(config.PROCESSED_DATA_DIR / "vocabs.txt")

    # 4. 模型
    model = InputMethodModel(vocab_size=tokenizer.vocab_size).to(device)

    # 5. 损失函数
    loss_fn = torch.nn.CrossEntropyLoss()
    # 6. 优化器
    optimizer = torch.optim.Adam(model.parameters(),lr=config.LEARNING_RATE)

    # 7. tensorboard writer
    writer = SummaryWriter(log_dir=config.LOGS_DIR / time.strftime("%Y-%m-%d_%H-%M-%S"))

    # 开始训练
    best_loss = float("inf")
    for epoch in range(1, 1 + config.EPOCHS):
        print(f"{'=' * 10} Epoch: {epoch} {'=' * 10}")
        loss = train_one_epoch(model, dataloader, loss_fn, optimizer, device,epoch)
        
        # 记录训练结果
        writer.add_scalar('loss',loss,epoch)
        
        # 保存模型
        if loss < best_loss:
            best_loss = loss
            torch.save(model.state_dict(),config.MODELS_DIR / "best.pth")
            print("模型保存成功")
            
    writer.close()
    
    
if __name__ == '__main__':
    train()