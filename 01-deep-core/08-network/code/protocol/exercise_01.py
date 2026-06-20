import json

from protocol import ChatMessage, Message, PlayerUpdate


def recreate_message(msgtype: str, payload: str) -> Message:

    msgcls = Message._registry[msgtype]
    # if msgtype == "ChatMessage":
    #     msgcls = ChatMessage
    # elif msgtype == "PlayerUpdate":
    #     msgcls = PlayerUpdate
    # else:
    #     raise RuntimeError(f"Not support {msgtype}")

    kwargs = json.loads(payload)
    # for key,cls in msgcls.__init__.__annotations__.items():
    #     if not isinstance(kwargs[key],cls):
    #         raise TypeError(f'{kwargs[key]} is {type(kwargs[key]).__name__} expect {cls.__name__}')

    return msgcls.from_untrust(**kwargs)


def test_recreator():
    msg1 = recreate_message(
        "ChatMessage", '{"playerid": "Pkmer", "text": "Hello World"}'
    )
    assert msg1 == ChatMessage("Pkmer", "Hello World")

    mgs2 = recreate_message(
        "PlayerUpdate", '{"playerid": "鲨鱼のJavthon", "x": 23, "y": 56}'
    )
    assert mgs2 == PlayerUpdate("鲨鱼のJavthon", 23, 56)
    print("Ok creator!")

    # A message of invalid type
    try:
        msg3 = recreate_message("HackerMsg", '{"x": 666}')
        assert False, "Why did this work?!?! Bad creator"
    except Exception as e:
        print("Good Creator!")

    # Message with incomplete arguments
    try:
        msg4 = recreate_message("PlayerUdpdate", '{"playerid": "Paula"}')
    except Exception as e:
        # Above message is missing fields for x/y. Could this be caught?
        print("Very Good Creator!")

    # Message with wrong argument types
    try:
        msg5 = recreate_message(
            "PlayerUpdate", '{"playerid": "Paula", "x": "two", "y": 123.45}'
        )
    except Exception as e:
        # The x and y values violate Python type-hints. Could this be caught?
        print(e)
        print("Excellent creator!")
