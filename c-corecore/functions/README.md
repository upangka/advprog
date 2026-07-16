# 函数的定义

无参数的形式`void`

下面的这种无参形式，居然可以在调用的时候传递参数。能够正常编译和运行
```c
void my_f(){}

int main(){
    my_f();
    my_f("hello","world");
}
```

生态中标准的写法,当没有接受参数的时候明确表明是`void`

```c
void my_f(void){}

int main(){
    my_f();
    // X error: too many arguments to function ‘my_f’
    // my_f("hello","world");
}
```