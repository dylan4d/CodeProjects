#include <iostream>
#include "ufo_functions.hpp"

int main() {
    greet();

    std::string codeword = "word";
    std::string answer = "____";

    int misses = 0;
    std::vector<char> incorrect;
    bool guess = false;

    char letter;

    while (answer != codeword && misses < 7) {   
        display_status(incorrect, answer);

        std::cout << "\n\nPlease enter your guess: ";
        std::cin >> letter;

        for (int i = 0; i < codeword.length(); i++){

            if (letter == codeword[i]) {
                answer[i] = letter;
                guess = true;
            }
        }

        if (guess) {
            std::cout << "\nCorrect!";
        } else {
            std::cout << "\nIncorrect!";
            incorrect.push_back(letter);
            misses++;
        }

        display_misses(misses);
        guess = false;
    }

    end_game(answer, codeword);
}
