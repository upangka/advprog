# 单行注释中有 spam
# 干扰选项

import re
from pathlib import Path

for path in Path(".").rglob("*.py"):
    if path.exists():
        with path.open("rt", encoding="utf-8") as f:
            for line in f:
                m = re.match(".*(#.*)$", line)
                if m:
                    comment = m.group(1)
                    if "spam" in comment:
                        print(comment)  # spam在代码一行的后面
