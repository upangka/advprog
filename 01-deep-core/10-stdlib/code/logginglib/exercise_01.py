import logging

# 配置的是root(RootLogger)
logging.basicConfig(level=logging.INFO)
logging.info("hi world")

# 取出root
print(logging.root)
print(logging.root.name)
# 默认的handle
print(logging.root.handlers)


