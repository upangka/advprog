"""
注意: 这个程序本身就存在漏洞bug
在exercise_05.py中我们写程序来测试这个程序, 从而找到elevator.py
程序中潜在的漏洞bug
"""

IDLE = "IDLE"  # 空闲：电梯门关着，没有任何请求，不移动
MOVINGUP = "MOVINGUP"  # 向上移动：电梯正在向上运行
MOVINGDOWN = "MOVINGDOWN"  # 向下移动：电梯正在向下运行
LOADINGUP = "LOADINGUP"  # 上客（向上）：电梯门开着，乘客进入，之后将继续向上
LOADINGDOWN = "LOADINGDOWN"  # 上客（向下）：电梯门开着，乘客进入，之后将继续向下
UNLOADING = "UNLOADING"  # 下客：电梯门开着，乘客离开，无后续请求，之后将返回 IDLE


class Elevator:
    def __init__(
        self, mode=IDLE, floor=1, destinations=(), up_requests=(), down_requests=()
    ):
        self.mode = mode  # Operational mode
        self.floor = floor  # Current floor
        self.destinations = (
            set(destinations) or set()
        )  # Set of all buttons pressed inside the car
        self.up_requests = (
            set(up_requests) or set()
        )  # Set of all "up" requests in the hallway
        self.down_requests = (
            set(down_requests) or set()
        )  # Set of all "down" requests in the hallway

    def __repr__(self):
        return f"Elevator({self.mode}, {self.floor}, {self.destinations}, {self.up_requests}, {self.down_requests})"

    def has_requests(self):
        return bool(self.destinations or self.up_requests or self.down_requests)

    def highest_request(self):
        return max(self.destinations | self.up_requests | self.down_requests)

    def lowest_request(self):
        return min(self.destinations | self.up_requests | self.down_requests)

    def handle_event(self, event_type, floor):
        # Events are tuples of the form ('event_type', floor)
        method = f"{self.mode}_{event_type}"
        getattr(self, method)(floor)
        return self.mode

    # IDLE Mode
    def IDLE_destination(self, floor):
        if floor > self.floor:
            self.destinations.add(floor)
            self.mode = MOVINGUP
        elif floor < self.floor:
            self.destinations.add(floor)
            self.mode = MOVINGDOWN
        else:
            self.mode = UNLOADING

    def IDLE_up_request(self, floor):
        if floor > self.floor:
            self.up_requests.add(floor)
            self.mode = MOVINGUP
        elif floor < self.floor:
            self.up_requests.add(floor)
            self.mode = MOVINGDOWN
        else:
            self.mode = LOADINGUP

    def IDLE_down_request(self, floor):
        if floor > self.floor:
            self.down_requests.add(floor)
            self.mode = MOVINGUP
        elif floor < self.floor:
            self.down_requests.add(floor)
            self.mode = MOVINGDOWN
        else:
            self.mode = LOADINGDOWN

    # MOVINGUP Mode
    def MOVINGUP_destination(self, floor):
        if floor > self.floor:
            self.destinations.add(floor)

    def MOVINGUP_up_request(self, floor):
        self.up_requests.add(floor)

    def MOVINGUP_down_request(self, floor):
        self.down_requests.add(floor)

    def MOVINGUP_floor_sensor(self, floor):
        self.floor = floor
        if floor in self.destinations:
            self.destinations.discard(floor)
            if not self.has_requests():
                self.mode = UNLOADING
            elif self.highest_request() > floor or floor in self.up_requests:
                self.mode = LOADINGUP
                self.up_requests.discard(floor)
            else:
                self.mode = LOADINGDOWN
                self.down_requests.discard(floor)
        elif floor in self.up_requests:
            self.mode = LOADINGUP
            self.up_requests.discard(floor)
        elif floor in self.down_requests and self.highest_request() == floor:
            self.mode = LOADINGDOWN
            self.down_requests.discard(floor)

    # MOVINGDOWN Mode
    def MOVINGDOWN_destination(self, floor):
        if floor < self.floor:
            self.destinations.add(floor)

    def MOVINGDOWN_up_request(self, floor):
        self.up_requests.add(floor)

    def MOVINGDOWN_down_request(self, floor):
        self.down_requests.add(floor)

    def MOVINGDOWN_floor_sensor(self, floor):
        self.floor = floor
        if floor in self.destinations:
            self.destinations.discard(floor)
            if not self.has_requests():
                self.mode = UNLOADING
            elif self.lowest_request() < floor or floor in self.down_requests:
                self.mode = LOADINGDOWN
                self.down_requests.discard(floor)
            else:
                self.mode = LOADINGUP
                self.up_requests.discard(floor)
        elif floor in self.down_requests:
            self.mode = LOADINGDOWN
            self.down_requests.discard(floor)
        elif floor in self.up_requests and self.lowest_request() == floor:
            self.mode = LOADINGUP
            self.up_requests.discard(floor)

    # LOADINGUP Mode
    def LOADINGUP_destination(self, floor):
        if floor > self.floor:
            self.destinations.add(floor)

    def LOADINGUP_up_request(self, floor):
        if floor != self.floor:
            self.up_requests.add(floor)

    def LOADINGUP_down_request(self, floor):
        self.down_requests.add(floor)

    def LOADINGUP_doors_close(self, floor):
        if not self.has_requests():
            self.mode = IDLE
        elif self.highest_request() > self.floor:
            self.mode = MOVINGUP
        elif self.lowest_request() == self.floor:
            self.mode = LOADINGDOWN
        else:
            self.mode = MOVINGDOWN

    # LOADINGDOWN Mode
    def LOADINGDOWN_destination(self, floor):
        if floor < self.floor:
            self.destinations.add(floor)

    def LOADINGDOWN_up_request(self, floor):
        self.up_requests.add(floor)

    def LOADINGDOWN_down_request(self, floor):
        if floor != self.floor:
            self.down_requests.add(floor)

    def LOADINGDOWN_doors_close(self, floor):
        if not self.has_requests():
            self.mode = IDLE
        elif self.lowest_request() < self.floor:
            self.mode = MOVINGDOWN
        elif self.highest_request() == self.floor:
            self.mode = LOADINGUP
        else:
            self.mode = MOVINGUP

    # UNLOADING Mode
    def UNLOADING_destination(self, floor):
        if floor > self.floor:
            self.destinations.add(floor)
            self.mode = LOADINGUP
        elif floor < self.floor:
            self.destinations.add(floor)
            self.mode = LOADINGDOWN

    def UNLOADING_up_request(self, floor):
        if floor != self.floor:
            self.up_requests.add(floor)

    def UNLOADING_down_request(self, floor):
        if floor != self.floor:
            self.down_requests.add(floor)

    def UNLOADING_doors_close(self, floor):
        if not self.has_requests():
            self.mode = IDLE
        elif self.highest_request() > self.floor:
            self.mode = MOVINGUP
        else:
            self.mode = MOVINGDOWN
