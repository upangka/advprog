"""
自定义数据集
"""
import pandas as pd
import torch
from torch.utils.data import Dataset,DataLoader
from config import PROCESSED_DATA_DIR,BATCH_SIZE

class InputmethodDataset(Dataset):
    def __init__(self,path):
        self.data = pd.read_json(path, lines=True, orient="records").to_dict(orient="records")
        
    def __len__(self):
        return len(self.data)
    
    def __getitem__(self, index):
        train_tense = torch.tensor(self.data[index]['input'],dtype=torch.long)
        test_tense = torch.tensor(self.data[index]['target'],dtype=torch.long)
        return train_tense,test_tense
    
    
def get_dataloader(train=True):
    path = PROCESSED_DATA_DIR / ('train.jsonl' if train else 'test.jsonl')
    dataset = InputmethodDataset(path)
    return DataLoader(dataset,batch_size=BATCH_SIZE)


if __name__ == '__main__':
    train_dataloader = get_dataloader()
    for input_tense,target_tense in train_dataloader:
        print(input_tense.shape)
        print(target_tense.shape)
        break