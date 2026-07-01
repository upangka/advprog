principal = 500000.0
rate = 0.05
payment = 2684.11
total_paid = 0.0

first_12months_pay = payment + 1000

for months in range(1, 13):
    principal = principal * (1 + rate / 12) - first_12months_pay
    total_paid += first_12months_pay


while principal > 0:
    # 本金先涨利息 再扣掉当月还款
    # 新本金 = 旧本金 + 旧本金 × 月利率
    #       = 旧本金 × (1 + 月利率)
    principal = principal * (1 + rate / 12) - payment
    total_paid = total_paid + payment
    months += 1

print(f"Total payment of {round(total_paid,2)} over {months} months.")
