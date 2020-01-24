# Sheriff-of-Nottingham
Ana-Maria Radu, 324CA, November 2019

While playing, each player can display a different strategy, but they all come back to the
Basic strategy at some point, which is why Greedy and Bribed extend the BasicPlayer class. Certain
methods that are common to all strategies are implemented in the Player abstract class.

The game flow is quite simple and implemented using two 'for' loops: for each round, each
player gets to be sheriff once. The others play their according merchant strategies.

The final score, meaning king and queen bonuses as well as profit for the goods on the
stand is computed only once, as each player's stand will display all the goods in the end (i.e it
is not cleared between each round, whereas the bag has to be cleared).

For lack of better ideas, I chose to remember each player's ID by appending it to their
final scores, which I am aware only works because there can be 5 players at most (had there ever
been more than 9, bad luck).
