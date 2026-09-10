# Target

通过可视化和代码示例理解数学的概念，建立直观的感受（**Good intuition**），而无须关心抽象的数学语言证明和讨论

# Linear algebra

1. [Week1-方程组](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week1-Systems-of-linear-equations/README.md)
   1. [Slides](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week1-Systems-of-linear-equations/week-1-slides.pdf)
   2. [Week1-Lab](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week1-Systems-of-linear-equations/code/README.md)
      1. [Lab-1: Introduction to numpy arrays](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week1-Systems-of-linear-equations/code/Lab_1_introduction_to_numpy_arrays.ipynb)
      2. [Lab-2 Linear system as matrices](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week1-Systems-of-linear-equations/code/Lab_2_linear_systems_as_matrices.ipynb)
2. [Week2-高斯消元法求解线性方程组](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week2-Solving-systems-of-linear-equations/README.md)
   1. [Slides](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week2-Solving-systems-of-linear-equations/C1_W2_slides.pdf)
   2. [Week2-Lab](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week2-Solving-systems-of-linear-equations/code/README.md)
      1. [Lab_1_solving_linear_systems_3_variables](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week2-Solving-systems-of-linear-equations/code/Lab_1_solving_linear_systems_3_variables.ipynb)
      2. [Lab_2_Gaussion_elimination](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week2-Solving-systems-of-linear-equations/code/Lab_2_Gaussion_elimination.ipynb)

3. [Week3-向量与线性变换](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week3-Vectors-and-Linear-Transformations/README.md)
   1. [Slides](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week3-Vectors-and-Linear-Transformations/C1_W3_slides.pdf)
   2. [Week3-Lab](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week3-Vectors-and-Linear-Transformations/code/README.md)
      1. [Lab1 vector operations](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week3-Vectors-and-Linear-Transformations/code/Lab_1_vector_operations.ipynb)
      2. [Lab2 matrix multiplication](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week3-Vectors-and-Linear-Transformations/code/Lab_2_matrix_multiplication.ipynb)
      3. [Lab3 linear transformations](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week3-Vectors-and-Linear-Transformations/code/Lab_3_linear_transformations.ipynb)
      4. [Lab4 linear transformations and neural networks](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week3-Vectors-and-Linear-Transformations/code/Lab_4_linear_transformations_and_neural_networks.ipynb)
4. [Week4-行列式与特征向量(PCA)](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week4-Determinants-and-Eigenvectors/README.md)
   1. [Slides](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week4-Determinants-and-Eigenvectors/week-4-slides.pdf)
   2. [Week4-Lab](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week4-Determinants-and-Eigenvectors/code/README.md)
      1. [Lab1 Interpreting eigenvalues and eigenvectors](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week4-Determinants-and-Eigenvectors/code/Lab_1_Interpreting_eigenvalues_and_eigenvectors.ipynb)
      2. [Lab2 Webpage navigation model and PCA](./courses/Linear-Algebra-for-Machine-Learning-and-Data-Science/Week4-Determinants-and-Eigenvectors/code/Lab_2_Application_of_Eigenvalues_and_Eigenvectors-Webpage-navigation-model-and-PCA.ipynb)

# Calculus

1. [Week1-导数与损失优化](./courses/Calculus-for-Machine-Learning-and-Data-Science/Week1-Derivatives-and-Optimization/README.md)
   1. [Slides](./courses/Calculus-for-Machine-Learning-and-Data-Science/Week1-Derivatives-and-Optimization/C2_W1.pdf)

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

# Resources

1. [Bilibili 线性代数](https://www.bilibili.com/video/BV1WfagzvEXM/?spm_id_from=333.1391.0.0&p=8&vd_source=f9745f81b4981bb1eca8c2d80be33ff9)
   1. [Deeplearning.ai](https://learn.deeplearning.ai/specializations/mathematics-for-machine-learning-and-data-science/lesson/u0bve/specialization-introduction?utm_source=home&utm_medium=course-landing-page&utm_campaign=summary-cta-button)
2. [Github 相关参考仓库](https://github.com/greyhatguy007/Mathematics-for-Machine-Learning-and-Data-Science-Specialization-Coursera)
3. [慕课参考资料](https://www.bilibili.com/video/BV1Hf4y1B7Bf/?spm_id_from=333.788.recommend_more_video.2&trackid=web_related_0.router-related-2589621-dpmnd.1788674566992.976&vd_source=f9745f81b4981bb1eca8c2d80be33ff9)
