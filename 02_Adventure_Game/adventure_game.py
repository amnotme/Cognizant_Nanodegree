import time
import random


intro_prompts = [
    "You find yourself standing in the middle of an eery village, "
    "filled with grass and yellow wildflowers.",
    "Rumor has it that a wicked fairie is somewhere around here, "
    "you just wish to get out.",
    "...",
    "However, you must find your way out.",
    "Careful of the things that lurk about."
]

outro_prompts = [
    "You've left the village.",
    "Never wanting to go back there anymore.",
    "The only memories that you're taking with you "
    "are the items you've collected thus far.",
    "You dig around your backpack and take them out",
    "ITEMS",
    "You close your pack and continue walking... never looking back again.",
]

prompts = {
    'start': "As you enter the village, the road splits. "
             "Which way will you go? (Left, Right, Straight, Back, Exit)",
    'again': "You are back at the entrance of the village. "
             "Which way will you go? (Left, Right, Straight, Back, Exit)",
    'directions': "Go Left, Right, Back, Straight, Exit?",
    'empty': "You have nothing in it.",
    'check': "You check your backpack.",
    'found': "You've found: ",
    'nothing': "There's nothing else here. ",
}

village_items = {
    'compass': "A compass to set you in the right path",
    'silver key': "A key with a silver crest",
    'bronze key': "A key with a broze crest",
    'gold key': "A key with a gold crest",
    'knife': "A sharp knife",
    'map': "A map that shows you the way",
    'lighter': "A lighter that could be used to set something on fire",
    'gasoline': "A small canister of gasoline.",
    'book': "A book containing a picture of... something engulfed "
            "in flames",
}

fight_scene = {
    'silver key': "You've opened the silver door",
    'bronze key': "You've opened the bronze door",
    'gold key': "You've opened the golden door",
    'knife': "You plunge the knife on the fairy's throat",
    'lighter': "You light the fairy on fire",
    'gasoline': "You throw the gasoline at the fairy, covering her eyes",
}


fairy_entrance_prompts = [
    "You've reached a bronze door.",
    "You open it. But there's a silver door in front of you now",
    "A golden gate is further up",
    "As soon as you open the door.  A heinous fairy tries to attack!",
    "This is your chance",
    "She's down! Quick use something sharp",
]


directions = [
    'left', 'right', 'straight', 'back', 'exit'
]

answers = [
    'yes', 'no', 'y', 'n'
]

locations = [
    "You see something. ",
    "What's that? ",
    "Is that...? ",
    "A chest ",
    "Hmmm... ",
    "Could it be? "
]

traverse_or_fight_selection = {
    1: "Something moved!",
    2: "Who's THERE?",
    3: "What the heck is that.",
    4: "It's so dark around here.",
    5: "fight",
    6: "Must be my imagination.",
    7: "This place is creepy",
    8: "Shit that was frightening",
    9: "Where am I?",
    0: "How did I get here?",
}


"""ANSI color code functions"""
def prRed(text): print("\033[91m {}\033[00m" .format(text))  # left
def prGreen(text): print("\033[92m {}\033[00m" .format(text))  # traverse
def prYellow(text): print("\033[93m {}\033[00m" .format(text))  # right
def prLightPurple(text): print("\033[94m {}\033[00m" .format(text))  # straight
def prPurple(text): print("\033[95m {}\033[00m" .format(text))  # back
def prCyan(text): print("\033[96m {}\033[00m" .format(text))  # start
def prLightGray(text): print("\033[97m {}\033[00m" .format(text))
def prBlack(text): print("\033[98m {}\033[00m" .format(text))


def color_prompt_pause(text, color_function):
    color_function(text)
    time.sleep(.6)


def intro():
    for prompt in intro_prompts:
        color_prompt_pause(prompt, prGreen)


def outro(items):
    for prompt in outro_prompts:
        if prompt == "ITEMS":
            if not items:
                color_prompt_pause(
                    "There's nothing in your backpack",
                    prGreen
                )
            else:
                for item in list(set(items)):
                    color_prompt_pause(item, prGreen)
        else:
            color_prompt_pause(prompt, prLightGray)


def check_bag(items):
    color_prompt_pause('Would you like to check your backpack?', prCyan)
    answer = (input()[0].lower())
    if answer in answers:
        if answer == 'y':
            color_prompt_pause(prompts['check'], prGreen)
            if not items:
                color_prompt_pause(prompts['empty'], prGreen)
            else:
                for item in list(set(items)):
                    color_prompt_pause(item, prLightGray)
    else:
        color_prompt_pause("umm... ?", prGreen)
        check_bag(items)


def paths(path, items):
    paths = {
        'left': go_left,
        'right': go_right,
        'back': go_back,
        'straight': go_straight,
    }
    if path == 'exit':
        return
    check_bag(items)
    paths[path](items)
    traverse_or_fight(items)


def format_response(respose, items=None):
    path = respose.lower().strip()
    if path in directions:
        color_prompt_pause(f"You've decided to go {path}", prCyan)
        paths(path, items)
    else:
        color_prompt_pause("umm... ?", prCyan)
        format_response(input(prompts['directions']), items)


def get_items_at_location(items):
    return random.choice(items), random.choice(locations)


def go_left(items):
    count = 0
    left_items = ['compass', 'silver key']
    for item in left_items:
        if item in items:
            count += 1
            continue
    if count < 2:
        found, location = get_items_at_location(left_items)
        color_prompt_pause(location, prRed)
        color_prompt_pause(prompts['found'] + village_items[found], prRed)
        items.append(found)
    else:
        color_prompt_pause(prompts['nothing'], prRed)


def go_right(items):
    count = 0
    right_items = ['bronze key', 'knife', 'book']
    for item in right_items:
        if item in items:
            count += 1
            continue
    if count < 3:
        found, location = get_items_at_location(right_items)
        color_prompt_pause(location, prYellow)
        color_prompt_pause(prompts['found'] + village_items[found], prYellow)
        items.append(found)
    else:
        color_prompt_pause(prompts['nothing'], prYellow)


def go_back(items):
    count = 0
    back_items = ['lighter', 'gold key']
    for item in back_items:
        if item in items:
            count += 1
            continue
    if count < 2:
        found, location = get_items_at_location(back_items)
        color_prompt_pause(location, prPurple)
        color_prompt_pause(prompts['found'] + village_items[found], prPurple)
        items.append(found)
    else:
        color_prompt_pause(prompts['nothing'], prPurple)


def go_straight(items):
    count = 0
    straight_items = ['gasoline', 'map']
    for item in straight_items:
        if item in items:
            count += 1
            continue
    if count < 2:
        found, location = get_items_at_location(straight_items)
        color_prompt_pause(location, prLightPurple)
        color_prompt_pause(
            prompts['found'] + village_items[found],
            prLightPurple
            )
        items.append(found)
    else:
        color_prompt_pause(prompts['nothing'], prLightPurple)


def traverse_or_fight(items):
    decision = random.randint(0, 9)
    if (
        traverse_or_fight_selection[decision] == "fight"
        or len(list(set(items))) > 8
    ):
        if (fight_the_fairy(items) < 6):
            color_prompt_pause(
                "Hmm. Would you like to continue exploring?",
                prCyan
                )
            response = input("(yes / no) ").lower()[0]
            while response not in answers:
                color_prompt_pause(
                    "Hmm. Would you like to continue exploring?",
                    prCyan
                    )
                response = input("(yes / no) ? ").lower()[0]
            if response == 'y':
                color_prompt_pause(prompts['directions'], prCyan)
                format_response(input(), items)
            else:
                return
    else:
        color_prompt_pause(traverse_or_fight_selection[decision], prGreen)
        color_prompt_pause(prompts['directions'], prCyan)
        format_response(input(), items)


def fight_the_fairy(items):
    idx = 0

    for prompt in fairy_entrance_prompts:
        if 'bronze key' in items and idx == 0:
            color_prompt_pause(prompt, prLightGray)
            color_prompt_pause(fight_scene['bronze key'], prLightGray)
            idx += 1
            continue

        if 'silver key' in items and idx == 1:
            color_prompt_pause(prompt, prLightGray)
            color_prompt_pause(fight_scene['silver key'], prLightGray)
            idx += 1
            continue

        if 'gold key' in items and idx == 2:
            color_prompt_pause(prompt, prLightGray)
            color_prompt_pause(fight_scene['gold key'], prLightGray)
            idx += 1
            continue

        if 'gasoline' in items and idx == 3:
            color_prompt_pause(prompt, prLightGray)
            color_prompt_pause(fight_scene['gasoline'], prLightGray)
            idx += 1
            continue

        if 'lighter' in items and idx == 4:
            color_prompt_pause(prompt, prLightGray)
            color_prompt_pause(fight_scene['lighter'], prLightGray)
            idx += 1
            continue

        if 'knife' in items and idx == 5:
            color_prompt_pause(prompt, prLightGray)
            color_prompt_pause(fight_scene['knife'], prLightGray)
            idx += 1
            continue

    if idx != 6:
        color_prompt_pause("A dark force has knocked you out.", prGreen)
        color_prompt_pause("You may need other items to proceed", prGreen)
    else:
        color_prompt_pause("You've done it. You defeated the fairy", prGreen)
        color_prompt_pause("It's time to go home", prGreen)

    return idx


def try_again():
    color_prompt_pause(
        "You've reached the end... "
        "Would you like to start again?", prCyan
        )
    response = input("(yes / no): ").lower()[0]
    while response not in answers:
        response = input("ummm... ( yes / no ) ? ").lower()[0]
    if response == 'y':
        return True
    return False


def game_start(start, again):

    items = []
    if start:
        color_prompt_pause(prompts['start'], prCyan)
        start = False

    if again:
        color_prompt_pause(prompts['again'], prCyan)
        again = False
    format_response(input(), items)
    again = try_again()

    return items, start, again


def main():
    start = True
    again = False
    while start or again:
        intro()
        end_items, start, again = game_start(start, again)
        if not again:
            outro(end_items)


if __name__ == '__main__':
    main()
