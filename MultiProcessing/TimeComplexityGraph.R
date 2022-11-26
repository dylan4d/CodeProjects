library(ggplot2)
library(dplyr)
library(ggthemes)

x <- c(20, 200, 1500, 800, 400, 1000)
y <- c(12.56, 136.85, 1046, 554.04, 266.48, 671.18)
runtime_data <- tibble(x, y)
container_x <- c(1000, 1500, 20, 600, 200, 800, 500)
container_y <- c(78.18, 120.98, 1.375, 48.14, 15.10, 61.23, 35.00)
runtime_container <- tibble(container_x, container_y)
vm_x <- c(20, 200, 600, 1200, 1500, 1300, 800, 1000, 400, 1400)
vm_y <- c(2.37, 18.40, 54.78, 116.85, 151.48, 127.42, 77.53, 96.52, 31.34, 109.62)
vm_data <- tibble(vm_x, vm_y)

p <- runtime_data %>%
  ggplot(aes(x, y)) +
  geom_point(size = 2) +
  geom_line() +
  
  ggthemes::theme_pander(lp = "left") +
  labs(
    title = "Time Complexity for Application",
    subtitle = "CS3204 Cloud Infrastructure",
    x = "Number of Iterations",
    y = "Time (seconds)") +
  
  xlim(min(x), max(x)) +
  ylim(min(y), max(y)) +
  
  scale_y_continuous(breaks = round(seq(0, max(y), by = 100), 1)) +
  
  geom_point(data = runtime_container, aes(x = container_x, y = container_y),
             size = 2, col = "cyan2") +
  geom_line(data = runtime_container, aes(x = container_x, y = container_y),
            col = "cyan2", linetype = "dashed") +
  
  geom_point(data = vm_data, aes(x = vm_x, y = vm_y),
             col = "red", size = 2) +
  geom_line(data = vm_data, aes(x = vm_x, y = vm_y),
            col = "red", linetype = "dashed") +
  
  annotate("text", x = 1000, y = 20, label="Container", col = "cyan2") +
  annotate("text", x = 1500, y = 1000, label="Standard") +
  annotate("text", x = 1300,  y = 180, label="Virtual Machine", col="red")

plot(p)
