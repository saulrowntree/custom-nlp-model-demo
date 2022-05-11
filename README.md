# Custom NLP Model Demo Code

A custom-written demo using Stanford NLP to accomplish the goal of finding and logging self-admitted technical debt. This code exists as a heavily modified version of https://github.com/Naplues/MAT, the code was stripped down to the NLP core component, with this converted into an abstract class and its methods made static, redundant methods etc. removed. NLP.prepareData was completely changed and the training & testing code heavily refactored. 

Simply run ***src/main/Main.java*** and observe the statistics calculated once the run has completed.

Testing set size can be modified by changing **TEST_SET_SIZE** in ***src/main/NLP.java***