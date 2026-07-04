import logging

# 配置的是root(RootLogger)
logging.basicConfig(level=logging.INFO)
logging.info("hi world")

# 取出root
print(logging.root)
print(logging.root.name)
# 默认的handle
print(logging.root.handlers)


root = logging.getLogger()
logger = logging.getLogger(__name__)
print("logger.parent is root:", logger.parent is root)
