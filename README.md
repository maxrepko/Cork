Cork is the work-in-progress chess game and chess move generator, as well as a way for me to challenge myself and give myself insight into something that I take for granted, specifically playing chess online through either my personal computer or my phone. This project focuses on solving the issues posed by creating a chess game that you could play against an AI in as a creative way as possible in as few lines of code as possible. For reference, see example below:
  
    Issue: Validatiting legality of knight movement, specifically checking whether the knight moves in a 2-1 order move. A 2-1 order move is either two vertical moves and one horizontal, or two horizontal mpves and one vertical.
  
    Possible solutions:
    1) I can just test all 8 possible variations. This is redundant and it fails to make the test space-efficient in terms of code length.
    2) I can test to see if the length of the vertical move (destination minus the origin of the piece) divided by the horizontal move (found same way as a vertical move) equals either 2.0 or 0.5, positive or negative (aka the ratio for a 2-1 move). The issue here is that it requires a second test to make sure that the player doesn't just make, for example, 4 moves vertically and 2 horizontally, meaning it will still have an either a 2.0 or 0.5 ratio for the quotient.
    3) I can apply the pythagoreon theorem to the knight's move. This will allow me to simplify the tests down to one test as all 8 possible moves result in the same result. Furthermore, this also takes care of the issue stated in the second possible solution because the pythagoreon value of 5 is only obtainable if in a^2 + b^2 = c, either a = 2 (-2) and b = 1 (-1), or a = 1 (-1) and b = 2 (-2). Any other values for either gives us a different result.

    Result: I went with solution 3 as it took just a few lines of code and really simplified the test.
