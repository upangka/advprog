from datetime import datetime, date


def main():
    slogon = "鲨鱼のJavthon认真学 《Machine Learning Specialization》!"
    start_date = datetime.strptime("2026/09/14", "%Y/%m/%d").date()
    days = (date.today() - start_date).days

    print(f"""\
{"─" * 80}
Hi,friends:
    {slogon}
    已经坚持了 {days} 天！
{"─" * 80}
""")


if __name__ == "__main__":
    main()