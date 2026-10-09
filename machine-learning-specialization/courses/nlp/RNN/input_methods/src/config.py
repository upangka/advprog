"""
超参数配置
"""
from pathlib import Path

ROOT_DIR = Path(__file__).parent.parent

# 绝对路径
RAW_DATA_DIR = ROOT_DIR / "data" / "raw"
PROCESSED_DATA_DIR = ROOT_DIR / "data" / "processed"
LOGS_DIR = ROOT_DIR / "logs"
MODELS_DIR = ROOT_DIR / "models"

# Sequence Length（序列长度）
SEQ_LEN = 5
BATCH_SIZE = 64
# 词向量的维度
EMBEDDING_DIM = 128