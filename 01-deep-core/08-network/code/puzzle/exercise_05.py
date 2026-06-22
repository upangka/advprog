from elevator import Elevator
from exercise_03 import Fail
from exercise_04 import elevator_domain, elevator_spec, find_solutions


def all_possiable_events(state):
    if state["mode"] == "MOVINGUP":
        yield ("floor_sensor", state["floor"] + 1)
    elif state["mode"] == "MOVINGDOWN":
        yield ("floor_sensor", state["floor"] - 1)
    elif state["mode"] in {"LOADINGUP", "LOADINGDOWN", "UNLOADING"}:
        yield ("doors_close", state["floor"])

    # Any button in car could be pressed
    for floor in range(1, 6):
        yield ("destination", floor)

    # Any up button
    for floor in range(1, 5):
        yield ("up_request", floor)

    # Any down button
    for floor in range(2, 6):
        yield ("down_request", floor)


def test_elevator():
    # Iterate over all good elevators
    for state in find_solutions(elevator_spec, elevator_domain):
        # Try all possiable events on the elevator that can occur in this state
        for event, floor in all_possiable_events(state):
            # Create an elevator instance
            elevator = Elevator(**state)
            # Try the event
            elevator.handle_event(event, floor)
            # Verify that it's still a good elevator using the
            # spec. If this fails,then there's a bug in the elevator
            # software
            try:
                elevator_spec(**vars(elevator))
            except Fail:
                print(f"BAD! {state} : ({event} ,{floor}) -> {elevator}")


if __name__ == "__main__":
    test_elevator()
