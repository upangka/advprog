import re
from pathlib import Path


def get_paths(topdir, pattern):
    for path in Path(topdir).rglob(pattern):
        if path.exists():
            yield path


def get_files(paths):
    for path in paths:
        with path.open("rt", encoding="utf-8") as f:
            yield f


def get_lines(files):
    for file in files:
        # 一个文件有多行，这里使用yield from委托的机制
        # yield from Iterable 也是可以的
        yield from file


def get_comments(lines):
    for line in lines:
        m = re.match(".*(#.*)$", line)
        if m:
            yield m.group(1)


def print_matching(comments, substring):
    for comment in comments:
        if substring in comment:
            print(comment)


if __name__ == "__main__":
    paths = get_paths(".", "*.py")
    files = get_files(paths)
    lines = get_lines(files)
    comments = get_comments(lines)
    print_matching(comments, "spam")
