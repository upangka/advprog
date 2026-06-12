from pathlib import Path

import matplotlib.pyplot as plt
import numpy as np

# Get the directory where this script is located
script_dir = Path(__file__).parent
plt.style.use(script_dir / "deeplearning.mplstyle")

x_train = np.array([1.0, 2.0])
y_train = np.array([300.0, 500.0])

# Plot the data points
plt.scatter(x_train, y_train, marker="x", c="r")
# Set the title
plt.title("Housing Prices")
# Set the y-axis label
plt.ylabel("Price (in 1000s of dollars)")
# Set the x-axis label
plt.xlabel("Size (1000 sqft)")
plt.show()
