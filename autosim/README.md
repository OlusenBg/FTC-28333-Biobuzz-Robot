# autosim: BIOBUZZ AUTO planner

A fast simulator for the 30 second AUTO, used to find the best AUTO plan
and later to train a neural network that picks the next action.

**The numbers in `config.py` are placeholders.** Values tagged `UNVERIFIED`
come from the manual or guesses and must be checked. Values tagged `MEASURE`
must be timed on the real robot. Until then, the results are only examples.

## How it works

- The robot's AUTO is a plan: a list of high level actions
  (`collect` at a FLOWER, `launch` into the HIVE, `park`).
  On the robot, each action becomes a Pedro Pathing path plus a subsystem routine.
- The sim adds up how long each action takes (with random noise and misses)
  and scores LEAVE, PARK and HIVE TIPs until 30 seconds run out.
- The alliance partner is simulated too (`partners.py`), because we don't
  control who we're paired with.

## Run it

Needs only Python 3, no extra packages.

```
python3 -m autosim.search --start red_audience --partner idle
python3 -m autosim.search --start red_far --partner one_cycle --runs 500
```

It prints the best plans with their average and worst score.

## Next steps

1. Fix the `UNVERIFIED` values from the Competition Manual, Section 10.
2. Time the robot and fill in the `MEASURE` values.
3. Add a Gymnasium wrapper and train a shared policy (start position and
   partner plan as inputs), then compare it to `search.py`.
