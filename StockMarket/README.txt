Simple Stock Market

1.  All prices are represented in pennies, as stated in caption of Table 1.
2.  On the assignment sheet, Table 2. Formula for P/E Ratio is stated as "Price / Dividend".  With "Dividend" I presumed it referred to "Last Dividend" from Table 1. 
3.  I judged the structure of the problem to be simple enough not to warrant any major design patters, again I deemed they will just add unnecessary complexity.
4.  ‘/StockMarket/src/main/java/superSimpleStockMarket/Application.java’ driver class is added, purely for demo reasons i.e. to allow the markers to play with :)
5.  I intentionally omitted any comments in the code; I tried to write unit tests and the code itself in a manner such that hopefully they'll be enough to document the programs behaviour.  
    Also comments usually go stale or are in danger to be (from my experience over the last year or so), so personally not a big fan of comments in code unless absolutely necessary :)
6.  I decided not to write unit tests for the toString() methods. In my humble opinion there’s nothing to gain from them, a part of test code coverage. 
7.  I tried to follow TDD as much as possible, but at some points I cheated and wrote the production code before writing unit tests.  I also sometimes skipped smaller intermediate steps and wrote tests that covered features rather than smaller units of work, but whenever I did I used Ctrl +1 as much as possible to let my tests write the corresponding missing production code. :) 
8.  Finally, I had no code reviews just my own tired eyes going over the code, so please forgive any negligence (bugs) on my part, as much as possible. :)

Thanks for taking the time to look at my code.

Johannes
