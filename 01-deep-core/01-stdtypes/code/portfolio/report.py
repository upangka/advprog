import portfolio
width = 15

def make_report(portfolio:list):
    """
    Print a report
    """
    portfolio.sort(key=lambda h: h['shares'] * h['price'],reverse=True)
    print(f"|{'name':^{width}}|{'shares':^{width}}|{'price':^{width}}|{'value':^{width}}|")
    print("|" + ('-' * width + '|') * 4)
    

    
