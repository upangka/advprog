def change_to_fahrenheit(celsius):
    """将摄氏度转化为华氏度"""
    return int((celsius * 9 / 5) + 32)


celsius = int(input())
fahrenheit = change_to_fahrenheit(celsius)
print(fahrenheit)
