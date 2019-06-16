**ShatteredDonations**
*A simple, EULA-compliant donation plugin.*
___

Defaults:
**config.yml**
```yaml
# Should all players get the same reward?
sameReward: true
# Set to false to use chat messages instead.
titles: true
# A keyless list of rewards. Type, weight, and name are required on all rewards.
rewards:
    # The type of reward. Defaults are 'command,' 'stat,' 'mob,' and 'effect.'
  - type: command
    # The weight of the reward. The number out of the total weight that this reward will be selected.
    weight: 1
    # The display name for this reward.
    name: "&aWood"
    # The command to be run. Parses placeholders as well as %username% for the player's username.
    command: give %username% minecraft:log 64
  - type: stat
    weight: 1
    name: "&cHunger"
    # The stat to be adjusted. 'HUNGER' and 'HEALTH' are available.
    stat: HUNGER
    # The amount the stat should be adjusted by.
    value: -5
  - type: stat
    weight: 1
    stat: HUNGER
    value: 20
    # Defaults to relative. Absolute sets the value to this, instead of adding the value to the current value.
    operation: ABSOLUTE
    name: "&aFeeding"
  - type: stat
    weight: 1
    stat: HEALTH
    value: 20
    name: "&aHealing"
  - type: mob
    mob: ZOMBIE
    weight: 1
    radius: 10
    amount: 5
    name: "&cZombies"
  - type: effect
    weight: 1
    effect: POISON
    power: 1
    # Duration in seconds.
    duration: 30.0
    name: "&cPoison"
```

**messages.yml**
```yaml
reward-main: "&a%player% &fhas challenged the fates."
reward-sub: "The fates have responded with: %reward%&f!"
donate: "You can donate at our website, &a%site%&f!"
no-permission: "&cYou do not have permission to use that."
not-enough-args: "&cNot enough arguments. Found &f%argc%&c, required &f%reqc%&c."
player-not-found: "&cPlayer not found."
prefix: "&aShatteredDonations &7// &f"
```