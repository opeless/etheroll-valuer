package com.dave.etheroll.test;

public class DiceValuer {

	public static void main(String[] args) {
		DiceValuer w = new DiceValuer();
		w.run();
	}

	public void run() {

		double noOfDiceTokens=7001623.0;
		int edge = 99;
		int iterations = 50;
		int initBankroll = 10000;
		int noOfBetsPerPeriod = 3000 * 7 * 12;
		int maxPercentBankrollWin = 1;
		double maximumBetEver=2.5;
		double bankRoll = initBankroll;
		int payout = 0;
		int bankrupt = 0;
		double profit = 0;
		double startBank;
		double gas = 0.005;
		double periodBets=0;
		for (int i = 0; i < iterations; i++) {
			// System.out.println("Period: "+i+" Bank Start: "+bankRoll);
			startBank = bankRoll;
            periodBets=0;
			
			for (int x = 0; x < noOfBetsPerPeriod; x++) {

				int myGuess = (int) (Math.random() * 99) + 1;
				double odds = ((((100.0 - ((myGuess - 1)))) / ((myGuess - 1.0)) + 1.0) * (edge/100.0)) - 1.0;
				double maxBet = bankRoll * (maxPercentBankrollWin/100.0) / odds;
				double actualBet = Math.min(maximumBetEver, Math.max(0.1, Math.random() * maxBet));

				int result = (int) (Math.random() * 100) + 1;

				if (myGuess > result) {
					// player wins
					bankRoll -= (actualBet * (odds));
				} else {
					// player loses
					bankRoll += actualBet;
				}

				bankRoll -= gas;
				periodBets +=actualBet;
				// System.out.println("Guess: "+myGuess+" (odds: "+odds+") maxBet: "+maxBet+"
				// Stake: "+actualBet+" Result: "+result+ " Bankroll:"+bankRoll);
				if (bankRoll < 0) {
					bankRoll = initBankroll;
					profit -= initBankroll;
					bankrupt ++;
				}
			}
			double endBank = bankRoll;
			if (bankRoll > initBankroll) {
				profit += (bankRoll - initBankroll);
				payout ++;
				bankRoll = initBankroll;
			}
			
			System.out.println("P: " + (i+1) + " " + (int) startBank + "->" + (int) endBank
					+ " Total Profit: " + (int) profit + " [" + payout + "," + bankrupt + "] Eth bet:"+(int)periodBets+" (EV:"+(int)(periodBets*(1-(edge/100.0)))+" av bet="+Math.round((double)periodBets*100/(double)(noOfBetsPerPeriod))/100.0+" total="
					+ ((1 / noOfDiceTokens) * profit) + " av.eth: " + (((1 / noOfDiceTokens) * profit) / (i + 1))+"/dice");
			
			startBank = bankRoll;

		}

	}
}
