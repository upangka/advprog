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
    return msgcls(**kwargs)


def test_recreator():
    msg1 = recreate_message(
        "ChatMessage", '{"playerid": "Pkmer", "text": "Hello World"}'
    )
    assert msg1 == ChatMessage("Pkmer", "Hello World")

    mgs2 = recreate_message(
        "PlayerUpdate", '{"playerid": "鲨鱼のJavthon", "x": 23, "y": 56}'
    )
    assert mgs2 == PlayerUpdate("鲨鱼のJavthon", 23, 56)
    print("Good creator!")
