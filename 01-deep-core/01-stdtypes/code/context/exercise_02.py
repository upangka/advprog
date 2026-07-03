class ListTransaction:
    def __init__(self, orig_list):
        self.orig_list = orig_list

    def __enter__(self):
        self.workingcopy = list(self.orig_list)
        return self.workingcopy

    def __exit__(self, ty, val, tb):
        if ty is None:
            self.orig_list[:] = self.workingcopy
        print(f"{ty=}, {val=}, {tb=}")
        return False


def main():
    import random

    orig_list = [random.randint(1, 10) for _ in range(10)]
    print(f"{orig_list=}")
    try:
        with ListTransaction(orig_list) as working:
            working.append(random.randint(20, 30))
            working.append(random.randint(20, 30))
            raise RuntimeError("Oops Something bad happened.")
    except Exception as err:
        pass
    print(f"{orig_list=}")
    print("-----------------------------------------------------")
    with ListTransaction(orig_list) as working:
        working.append(random.randint(20, 30))
        working.append(random.randint(20, 30))
    print(f"{orig_list=}")


if __name__ == "__main__":
    main()
