import time

def after(seconds,func):
    time.sleep(seconds)
    return func()