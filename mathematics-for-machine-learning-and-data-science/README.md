# uv jupyter lab

使用uv搭建实验环境搭建与安装相关package

```sh
pkmer@DESKTOP-2368UCO:code
$ uv init --no-workspace
Initialized project `code`

pkmer@DESKTOP-2368UCO:code
$ uv add numpy jupyter matplotlib
```

启动

```sh
uv run jupyter lab
# 后台启动
uv run jupyter lab &
```

# linear algebra

1. [Week1-奇异与非奇异](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week1-Systems-of-linear-equations/README.md)
   1. [Slides](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week1-Systems-of-linear-equations/week-1-slides.pdf)
   2. [Week1-Lab](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week1-Systems-of-linear-equations/code/README.md)
      1. [Lab-1: Introduction to numpy arrays](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week1-Systems-of-linear-equations/code/Lab_1_introduction_to_numpy_arrays.ipynb)
      2. [Lab-2 Linear system as matrices](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week1-Systems-of-linear-equations/code/Lab_2_linear_systems_as_matrices.ipynb)
2. [Week2-高斯消元法求解线性方程组](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week2-Solving-systems-of-linear-equations/README.md)
   1. [Slides](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week2-Solving-systems-of-linear-equations/C1_W2_slides.pdf)
   2. [Week2-Lab](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week2-Solving-systems-of-linear-equations/code/README.md)
      1. [Lab_1_solving_linear_systems_3_variables](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week2-Solving-systems-of-linear-equations/code/Lab_1_solving_linear_systems_3_variables.ipynb)
      2. [Lab_2_Gaussion_elimination](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week2-Solving-systems-of-linear-equations/code/Lab_2_Gaussion_elimination.ipynb)

# Resources

1. [Bilibili 线性代数](https://www.bilibili.com/video/BV1WfagzvEXM/?spm_id_from=333.1391.0.0&p=8&vd_source=f9745f81b4981bb1eca8c2d80be33ff9)
   1. [Deeplearning.ai](https://learn.deeplearning.ai/specializations/mathematics-for-machine-learning-and-data-science/lesson/u0bve/specialization-introduction?utm_source=home&utm_medium=course-landing-page&utm_campaign=summary-cta-button)
2. [Github 相关参考仓库](https://github.com/greyhatguy007/Mathematics-for-Machine-Learning-and-Data-Science-Specialization-Coursera)
3. [慕课参考资料](https://www.bilibili.com/video/BV1Hf4y1B7Bf/?spm_id_from=333.788.recommend_more_video.2&trackid=web_related_0.router-related-2589621-dpmnd.1788674566992.976&vd_source=f9745f81b4981bb1eca8c2d80be33ff9)
