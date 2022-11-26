/* 
scissors cuts paper
paper covers rock
rock crushes lizard
lizard poisons spock
spock smashes scissors
scissors decapitate lizard
lizard eats paper
paper disproves spock
spock vaporizes rock
rock crushes scissors
*/

#include <iostream>
#include <stdlib.h>

int main() {

    srand (time(NULL));

    int random_gen = rand() % 5 + 1;
    // choice will be 1, 2, 3, 4, 5

    int user = 0;

    std::cout << "===========================\n";
    std::cout << "rock paper scissors lizard spock";
    std::cout << "===========================\n";

    int rock = 1;
    int paper = 2;
    int scissors = 3;
    int lizard = 4;
    int spock = 5;
    
    std::cout << "Enter your choice!\n";
    std::cout << "1) Rock\n";
    std::cout << "2) Paper\n";
    std::cout << "3) Scissors\n";
    std::cout << "4) Lizard\n";
    std::cout << "5) Spock\n\n\n";
    std::cin >> user;
    std::cout << "computer: " << random_gen << "\n";

    if (user == rock && random_gen == scissors) {
        std::cout << "You Win!\n";
} else if (user == scissors && random_gen == paper) {
    std::cout << "You Win!\n";
} else if (user == paper && random_gen == rock) {
    std::cout << "You Win!\n";
} else if (user == lizard && random_gen == spock) {
    std::cout << "You Win!\n";
} else if (user == spock && random_gen == scissors) {
    std::cout << "You Win!\n";
} else if (user == scissors && random_gen == lizard) {
    std::cout << "You Win!\n";
} else if (user == lizard && random_gen == paper) {
    std::cout << "You Win!\n";
} else if (user == paper && random_gen == spock) {
    std::cout << "You Win!\n";
} else if (user == spock && random_gen == rock) {
    std::cout << "You Win!\n";
} else if (user == rock && random_gen == lizard) {
    std::cout << "You Win!\n";
} else if (user == random_gen) {
    std::cout << "Tie!\n";   
} else {
    std::cout << "You Lose!\n";  
}

}
