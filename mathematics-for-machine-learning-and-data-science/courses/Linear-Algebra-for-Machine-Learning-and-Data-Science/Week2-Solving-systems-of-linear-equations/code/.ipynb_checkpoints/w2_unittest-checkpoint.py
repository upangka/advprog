import numpy as np
import math

"""
np.allclose(a, b)       检查两个数组的所有元素是否近似相等（允许浮点误差）
np.isclose(a, b)        逐元素检查是否近似相等，返回布尔数组
np.logical_not()        对布尔数组取反
np.nonzero()            找出非零元素的索引（用于定位错误位置）
math.isclose()          检查两个浮点数是否近似相等
"""

def test_matrix(target_A, target_b):
    """
    测试 Exercise 1：矩阵 A 和向量 b 是否构造正确
    
    参数:
        target_A: 学生构造的系数矩阵 A
        target_b: 学生构造的常数向量 b
    
    测试内容:
        1. A 和 b 的形状是否正确
        2. A 和 b 的每个元素是否正确
    """
    
    successful_cases = 0  # 记录通过的测试数量
    failed_cases = []     # 记录失败的测试用例

    # 测试用例：预期正确的 A 和 b
    test_cases = [
        {
            "name": "default_check",
            "expected": {
                "A": np.array(
                    [[2, -1, 1, 1], [1, 2, -1, -1], [-1, 2, 2, 2], [1, -1, 2, 1]]
                ),
                "b": np.array([6, 3, 14, 8]),
            },
        },
    ]

    for test_case in test_cases:
        
        # ---- 测试 1：检查 A 的形状 ----
        try:
            assert target_A.shape == test_case["expected"]["A"].shape
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": test_case["expected"]["A"].shape,
                    "got": target_A.shape,
                }
            )
            print(
                f"Wrong shape of matrix A. \n\tExpected: {failed_cases[-1].get('expected')}.\n\tGot: {failed_cases[-1].get('got')}."
            )
            break  # 形状不对就停止，因为后续比较会报错

        # ---- 测试 2：检查 A 的每个元素是否相等 ----
        try:
            assert np.allclose(target_A, test_case["expected"]["A"])
            successful_cases += 1
        except:
            # np.nonzero 找出不相等的元素位置
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": np.nonzero(
                        np.logical_not(np.isclose(target_A, test_case["expected"]["A"]))
                    ),
                    "got": target_A,
                }
            )
            print(
                f"Wrong matrix of coefficients corresponding to the system of linear equations.\nCheck the coefficient in the row {failed_cases[-1].get('expected')[0][0] + 1}, column {failed_cases[-1].get('expected')[1][0] + 1}."
            )

        # ---- 测试 3：检查 b 的形状 ----
        try:
            assert target_b.shape == test_case["expected"]["b"].shape
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": test_case["expected"]["b"].shape,
                    "got": target_b.shape,
                }
            )
            print(
                f"Wrong shape of vector b. \n\tExpected: {failed_cases[-1].get('expected')}.\n\tGot: {failed_cases[-1].get('got')}."
            )
            break

        # ---- 测试 4：检查 b 的每个元素是否相等 ----
        try:
            assert np.allclose(target_b, test_case["expected"]["b"])
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": np.nonzero(
                        np.logical_not(np.isclose(target_b, test_case["expected"]["b"]))
                    ),
                    "got": target_b,
                }
            )
            print(
                f"Wrong vector of free coefficients corresponding to the system of linear equations.\nCheck element {failed_cases[-1].get('expected')[0][0] + 1} in the vector b."
            )

    # 输出测试结果（绿色 = 通过，红色 = 失败）
    if len(failed_cases) == 0:
        print("\033[92m All tests passed")
    else:
        print("\033[92m", successful_cases, " Tests passed")
        print("\033[91m", len(failed_cases), " Tests failed")


def test_det_and_solution_scipy(target_d, target_x):
    """
    测试 Exercise 2：行列式和求解是否正确
    
    参数:
        target_d: 学生计算的行列式 det(A)
        target_x: 学生求出的解向量 x
    """
    
    successful_cases = 0
    failed_cases = []

    test_cases = [
        {"name": "default_check", "expected": {
            "d": -17.0,
            "x": np.array([2, 3, 4, 1])},
        },
    ]

    for test_case in test_cases:
        
        # ---- 测试 1：行列式是否正确（允许 1e-04 误差） ----
        try:
            assert math.isclose(target_d, test_case["expected"]["d"], abs_tol=1e-04) 
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": test_case["expected"]["d"],
                    "got": target_d,
                }
            )
            print(
                f"Wrong determinant of matrix A.\n\tExpected: {failed_cases[-1].get('expected')}.\n\tGot: {failed_cases[-1].get('got')}."
            )
        
        # ---- 测试 2：解向量的形状是否正确 ----
        try:
            assert target_x.shape == test_case["expected"]["x"].shape
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": test_case["expected"]["x"].shape,
                    "got": target_x.shape,
                }
            )
            print(
                f"Wrong shape of the solution vector. \n\tExpected: {failed_cases[-1].get('expected')}.\n\tGot: {failed_cases[-1].get('got')}."
            )
            break

        # ---- 测试 3：解向量的每个元素是否正确 ----
        try:
            assert np.allclose(target_x, test_case["expected"]["x"])
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": test_case["expected"]["x"],
                    "got": target_x,
                }
            )
            print(
                f"Wrong solution vector.\n\tExpected: {failed_cases[-1].get('expected')}.\n\tGot: {failed_cases[-1].get('got')}."
            )

    if len(failed_cases) == 0:
        print("\033[92m All tests passed")
    else:
        print("\033[92m", successful_cases, " Tests passed")
        print("\033[91m", len(failed_cases), " Tests failed")


def test_elementary_operations(target_MultiplyRow, target_AddRows, target_SwapRows):
    """
    测试 Exercise 3：三种初等行操作函数是否正确
    
    参数:
        target_MultiplyRow: 学生实现的 MultiplyRow 函数
        target_AddRows: 学生实现的 AddRows 函数
        target_SwapRows: 学生实现的 SwapRows 函数
    """
    
    successful_cases = 0
    failed_cases = []

    # 测试用例：输入矩阵 A，以及三种操作后的预期输出
    test_cases = [
        {
            "name": "default_check",
            "input": {
                "A": np.array(
                    [[1, -2, 3, -4], [-5, 6, -7, 8], [-4, 3, -2, 1], [8, -7, 6, -5]]
                )
            },
            "expected": {
                # MultiplyRow(A, 2, -2)：第 3 行（索引 2）乘以 -2
                "A_MultiplyRow": np.array(
                    [[1, -2, 3, -4], [-5, 6, -7, 8], [8, -6, 4, -2], [8, -7, 6, -5]]
                ),
                # AddRows(A, 0, 2, 4)：第 1 行 × 4 加到第 3 行
                "A_AddRows": np.array(
                    [[1, -2, 3, -4], [-5, 6, -7, 8], [0, -5, 10, -15], [8, -7, 6, -5]]
                ),
                # SwapRows(A, 0, 2)：交换第 1 行和第 3 行
                "A_SwapRows": np.array(
                    [[-4, 3, -2, 1], [-5, 6, -7, 8], [1, -2, 3, -4], [8, -7, 6, -5]]
                ),
            },
        },
    ]

    for test_case in test_cases:
        # 执行学生写的三个函数
        result_MultiplyRow = target_MultiplyRow(test_case["input"]["A"], 2, -2)
        result_AddRows = target_AddRows(test_case["input"]["A"], 0, 2, 4)
        result_SwapRows = target_SwapRows(test_case["input"]["A"], 0, 2)

        # ---- 测试 MultiplyRow ----
        try:
            assert result_MultiplyRow.shape == test_case["expected"]["A_MultiplyRow"].shape
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": test_case["expected"]["A_MultiplyRow"].shape,
                    "got": result_MultiplyRow.shape,
                }
            )
            print(
                f"Wrong shape of the output matrix. Check MultiplyRow function. \n\tExpected: {failed_cases[-1].get('expected')}.\n\tGot: {failed_cases[-1].get('got')}."
            )

        try:
            assert np.allclose(result_MultiplyRow, test_case["expected"]["A_MultiplyRow"])
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": test_case["expected"]["A_MultiplyRow"],
                    "got": result_MultiplyRow,
                }
            )
            print(
                f"Wrong output matrix. Check MultiplyRow function. \n\tExpected: \n{failed_cases[-1].get('expected')}\n\tGot: \n{failed_cases[-1].get('got')}"
            )

        # ---- 测试 AddRows ----
        try:
            assert result_AddRows.shape == test_case["expected"]["A_AddRows"].shape
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": test_case["expected"]["A_AddRows"].shape,
                    "got": result_AddRows.shape,
                }
            )
            print(
                f"Wrong shape of the output matrix. Check AddRows function. \n\tExpected: {failed_cases[-1].get('expected')}.\n\tGot: {failed_cases[-1].get('got')}."
            )

        try:
            assert np.allclose(result_AddRows, test_case["expected"]["A_AddRows"])
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": test_case["expected"]["A_AddRows"],
                    "got": result_AddRows,
                }
            )
            print(
                f"Wrong output matrix. Check AddRows function. \n\tExpected: \n{failed_cases[-1].get('expected')}\n\tGot: \n{failed_cases[-1].get('got')}"
            )

        # ---- 测试 SwapRows ----
        try:
            assert result_SwapRows.shape == test_case["expected"]["A_SwapRows"].shape
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": test_case["expected"]["A_SwapRows"].shape,
                    "got": result_SwapRows.shape,
                }
            )
            print(
                f"Wrong shape of the output matrix. Check SwapRows function. \n\tExpected: {failed_cases[-1].get('expected')}.\n\tGot: {failed_cases[-1].get('got')}."
            )

        try:
            assert np.allclose(result_SwapRows, test_case["expected"]["A_SwapRows"])
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": test_case["expected"]["A_SwapRows"],
                    "got": result_SwapRows,
                }
            )
            print(
                f"Wrong output matrix. Check SwapRows function. \n\tExpected: \n{failed_cases[-1].get('expected')}\n\tGot: \n{failed_cases[-1].get('got')}"
            )

    if len(failed_cases) == 0:
        print("\033[92m All tests passed")
    else:
        print("\033[92m", successful_cases, " Tests passed")
        print("\033[91m", len(failed_cases), " Tests failed")


def test_augmented_to_ref(target):
    """
    测试 Exercise 4：行化简到行阶梯形是否正确
    
    参数:
        target: 学生实现的 augmented_to_ref 函数
    """
    
    successful_cases = 0
    failed_cases = []

    test_cases = [
        {
            "name": "default_check",
            "input": {
                "A": np.array(
                    [[2, -1, 1, 1], [1, 2, -1, -1], [-1, 2, 2, 2], [1, -1, 2, 1]]
                ),
                "b": np.array([6, 3, 14, 8]),
            },
            "expected": {
                "A_ref": np.array(
                    [
                        [1, 2, -1, -1, 3],
                        [0, 1, 4, 3, 22],
                        [0, 0, 1, 3, 7],
                        [0, 0, 0, 1, 1],
                    ]
                )
            },
        },
        {
            "name": "identity_matrix",
            "input": {
                "A": np.array([[1, 0, 0, 0], [0, 1, 0, 0], [0, 0, 1, 0], [0, 0, 0, 1]]),
                "b": np.array([1, 1, 1, 1]),
            },
            "expected": {
                "A_ref": np.array(
                    [
                        [0, 1, 0, 0, 1],
                        [0, 0, 1, 1, 2],
                        [2, -1, 1, -2, 0],
                        [0, 0, 0, -1, 0],
                    ]
                )
            },
        },
    ]

    for test_case in test_cases:
        result = target(test_case["input"]["A"], test_case["input"]["b"])

        # ---- 测试：输出矩阵的形状是否正确 ----
        try:
            assert result.shape == test_case["expected"]["A_ref"].shape
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": test_case["expected"]["A_ref"].shape,
                    "got": result.shape,
                }
            )
            print(
                f"Test case \"{failed_cases[-1].get('name')}\". Wrong shape of the output matrix. Check horizontal stack of matrix A and vector b. \n\tExpected: {failed_cases[-1].get('expected')}.\n\tGot: {failed_cases[-1].get('got')}."
            )

        # ---- 测试：输出矩阵的每个元素是否正确 ----
        try:
            assert np.allclose(result, test_case["expected"]["A_ref"])
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": test_case["expected"]["A_ref"],
                    "got": result,
                }
            )
            print(
                f"Test case \"{failed_cases[-1].get('name')}\". Wrong output matrix. Check row reduction operations. \n\tExpected: \n{failed_cases[-1].get('expected')}\n\tGot: \n{failed_cases[-1].get('got')}"
            )

    if len(failed_cases) == 0:
        print("\033[92m All tests passed")
    else:
        print("\033[92m", successful_cases, " Tests passed")
        print("\033[91m", len(failed_cases), " Tests failed")


def test_solution_elimination(target_x_1, target_x_2, target_x_3, target_x_4):
    """
    测试 Exercise 5：回代法求解是否正确
    
    参数:
        target_x_1, target_x_2, target_x_3, target_x_4: 学生求出的四个解
    """
    
    successful_cases = 0
    failed_cases = []

    test_cases = [
        {
            "name": "default_check",
            "expected": {"x_1": 2, "x_2": 3, "x_3": 4, "x_4": 1},
        },
    ]

    for test_case in test_cases:
        # 逐个检查 x_1, x_2, x_3, x_4
        for i, target_x_i in enumerate([target_x_1, target_x_2, target_x_3, target_x_4]):
            try:
                assert target_x_i == test_case["expected"]["x_" + str(i+1)]
                successful_cases += 1
            except:
                failed_cases.append(
                    {
                        "name": test_case["name"],
                        "expected": test_case["expected"]["x_" + str(i+1)],
                        "got": target_x_i,
                    }
                )
                print(
                    f"Wrong value of x_{(i+1)}.\n\tExpected: {failed_cases[-1].get('expected')}.\n\tGot: {failed_cases[-1].get('got')}."
                )

    if len(failed_cases) == 0:
        print("\033[92m All tests passed")
    else:
        print("\033[92m", successful_cases, " Tests passed")
        print("\033[91m", len(failed_cases), " Tests failed")


def test_ref_to_diagonal(target):
    """
    测试 Exercise 6：行阶梯形 → 对角形 的化简是否正确
    
    参数:
        target: 学生实现的 ref_to_diagonal 函数
    """
    
    successful_cases = 0
    failed_cases = []

    test_cases = [
        {
            "name": "default_check",
            "input": {
                "A_ref": np.array(
                    [
                        [1, 2, -1, -1, 3],
                        [0, 1, 4, 3, 22],
                        [0, 0, 1, 3, 7],
                        [0, 0, 0, 1, 1],
                    ]
                )
            },
            "expected": {
                "A_diag": np.array(
                    [
                        [1, 0, 0, 0, 2],
                        [0, 1, 0, 0, 3],
                        [0, 0, 1, 0, 4],
                        [0, 0, 0, 1, 1],
                    ]
                )
            },
        },
        {
            "name": "identity_matrix",
            "input": {
                "A_ref": np.array(
                    [[1, 0, 0, 0, 1], [0, 1, 0, 0, 1], [0, 0, 1, 0, 1], [0, 0, 0, 1, 1]]
                )
            },
            "expected": {
                "A_diag": np.array(
                    [
                        [1, -2, 9, -20, -12],
                        [0, 1, -4, 9, 6],
                        [0, 0, 1, -3, -2],
                        [0, 0, 0, 1, 1],
                    ]
                )
            },
        },
    ]

    for test_case in test_cases:
        result = target(test_case["input"]["A_ref"])

        # ---- 测试：输出矩阵的形状是否正确 ----
        try:
            assert result.shape == test_case["expected"]["A_diag"].shape
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": test_case["expected"]["A_diag"].shape,
                    "got": result.shape,
                }
            )
            print(
                f"Test case \"{failed_cases[-1].get('name')}\". Wrong shape of the output matrix. \n\tExpected: {failed_cases[-1].get('expected')}.\n\tGot: {failed_cases[-1].get('got')}."
            )

        # ---- 测试：输出矩阵的每个元素是否正确 ----
        try:
            assert np.allclose(result, test_case["expected"]["A_diag"])
            successful_cases += 1
        except:
            failed_cases.append(
                {
                    "name": test_case["name"],
                    "expected": test_case["expected"]["A_diag"],
                    "got": result,
                }
            )
            print(
                f"Test case \"{failed_cases[-1].get('name')}\". Wrong output matrix. Check row reduction operations. \n\tExpected: \n{failed_cases[-1].get('expected')}\n\tGot: \n{failed_cases[-1].get('got')}"
            )

    if len(failed_cases) == 0:
        print("\033[92m All tests passed")
    else:
        print("\033[92m", successful_cases, " Tests passed")
        print("\033[91m", len(failed_cases), " Tests failed")