injection.df <- read.table('//Users//dylan//OneDrive//Documents//School Files Y2//IntroductionToRegression//Injection.txt', header = T)
attach(injection.df)
head(injection.df)

options(contrasts = c(factor = 'contr.treatment', ordered = 'contr.poly'))
'Question1'
#A//
plot(injection.df$Time, injection.df$Conc, main = 'Conc VS. Time', xlab = 'Time', ylab = 'Conc')
#conc should be on the y axis due to it being the variable impacted by the change in time
#moderate negative linear relationship
injection.lm <- lm(injection.df$Conc ~ injection.df$Time)
abline(injection.lm)

#B//
summary(injection.lm)
#Intercept of 70.0733
#When t=0, conc = 70.0733. Expected injected dose is 70.0733
#Slope = -7.1933, for one increase in time the concentration reduces by 7.1933.

#C//
#0.4202 as standard error
-7.1933 + (0.4202 * qt(0.975, 296))
-7.1933 - (0.4202 * qt(0.975, 296))
#95% confident true slope lies between -6.3664 and -8.0202

#D//
#No, -9 is not contained in the CI
#H0: B1 = -9, H1: B1 != -9

#E//
#49.58% of the variability in Conc can be explained by Time
#No time is useful, but another variable would help.
#Add another variable that would further explain the variability in Cone

'Question 2'
#A//
injection.df.lm <- lm(Conc ~ Time + Dose + Weight + Age)
summary(injection.df.lm)
#strong negative correlation between conc and time
#moderate positive correlation between dose and time
#negligible correlation between conc and weight
#negligible correlation between conc and age
#time and dose are likely useful predictors, unlike weight and age

#B//
#-7.1845325 for a one unit increase in time the concentration reduces by -7.1845325
#assuming all other variables remain constant.

#C//
#the effect of time is now adjusted for the effects of the other predictors.
#the correlation between the predictors are negligible

#D//
#F stat of 805.8 and pval < 2.2e^16
#Values are significant so we reject H0
#At least one of the variables should be included in the model.

#E//
#H0: B3=B4=0
NoWeightAge.lm <- lm(Conc ~ Time + Dose)
anova(injection.df.lm, NoWeightAge.lm)
#Fval of 0.2044 and pval of 0.8152
#do not have sufficient evidence to reject the null hypothesis.
#Yes, they should be dropped from the model.

#F//
#Age, largest pval. (0.990)


'Question 3'
#A//
#residual is the difference between the fitted value and observed value
resid(injection.df.lm)[1]
#under-estimation of 4.335488 for the first case

#B//
#Case with substantially large residual
#If the modulus of the studentised residual is above 2
h <- lm.influence(injection.df.lm)$hat #leverages
e <- resid(injection.df.lm) #residuals
s <- summary(injection.df.lm)$sigma
r <- e / (s * (1- h)^0.5)
plot(r, type = 'h', main = 'Plot of Studentised Residuals vs. Obervation Number', ylab = 'Studentised Residuals', ylim = c(-4,4))
abline(h=c(-2,2), lty = 2)
#identify(r)
#5 percent of 300 cases is 15 cases
#14 outliers

#C//
plot(h, type = "h", main = "Plot of Leverage vs. Observation Number", ylab = 'Leverage')
#said to be a case of high leverage if the observed x is far from the mean of the xs
#5 cases
#identify(h)
#Case 294
head(injection.df, 94)
#Dose, Time, and Head are all maxima.

#D//
pprime <- length(coef(injection.df.lm))
d <- (1/ pprime) * (h / (1-h)) * r^2
plot(d, type = 'h', main = 'Plot of Cooks Distance vs. Observation Number', ylab = 'Cooks Distance')
#identify(d)

#E//
#Investigate case 22 and 156 as it is high influence.

'Question 4'
#A//

#ScatterPlot -> linear relationship constant variance
#Residual V Fitted Values -> Linear relationship and Constant Variance
#Histogram -> Approximately Normal Residuals
#Normal Probability Plot -> Approx Normal Residuals
par(mfrow = c(2, 2))
q4.1.lm <- lm(Conc ~ Time)
scatter.smooth(Time, Conc, main = 'Scatter.smooth Plot')

plot(fitted(q4.1.lm), resid(q4.1.lm), main = 'Plot of Residual V Fitted Values')
abline(h=0, lty = 2)

hist(resid(q4.1.lm), main = 'Histogram of Residuals')
qqnorm(resid(q4.1.lm), main = 'Normal Probability of Residuals')
qqline(resid(q4.1.lm))
par(mfrow = c(1,1))

#ScatterPlot -> non-linear relationship constant variance
#Residual V Fitted Values -> Non-linear relationship constant variance
#Histogram -> Normal residuals
#Normal Probability Plot -> Normal Residuals
par(mfrow = c(2, 2))
q4.2.lm <- lm(Conc ~ sqrt(Time))
scatter.smooth(sqrt(Time), Conc, main = 'Scatter.smooth Plot')

plot(fitted(q4.2.lm), resid(q4.2.lm), main = 'Plot of Residual V Fitted Values')
abline(h=0, lty = 2)

hist(resid(q4.2.lm), main = 'Histogram of Residuals')
qqnorm(resid(q4.2.lm), main = 'Normal Probability of Residuals')
qqline(resid(q4.2.lm))
par(mfrow = c(1,1))


#ScatterPlot -> non-linear relationship constant variance
#Residual V Fitted Values -> Non-linear relationship and constant variance
#Histogram -> Normal Residuals
#Normal Probability Plot -> Normal Residuals
par(mfrow = c(2,2))
q4.3.lm <- lm(Conc ~ log(Time))
scatter.smooth(log(Time), Conc, main = 'Scatter.smooth Plot')

plot(fitted(q4.3.lm), resid(q4.3.lm), main = 'Plot of Residual V Fitted Values')
abline(h=0, lty = 2)

hist(resid(q4.3.lm), main = 'Histogram of Residuals')
qqnorm(resid(q4.3.lm), main = 'Normal Probability of Residuals')
qqline(resid(q4.3.lm))
par(mfrow = c(1,1))

#B//
#I would choose model q4.1.lm as the diagnostic plots that best satifies the assumptions

#C//
#Exploring further models should be considered, the assumption of normality is not completely satisfied


'Question 5'
qualityMeans.df <- read.table('//Users//dylan//OneDrive//Documents//School Files Y2//IntroductionToRegression//Q5 Quality Means.txt', header = T)
attach(qualityMeans.df)
print(qualityMeans.df)
#A//
q5.weighted.lm <- lm(MeanWeight ~ Week, weights = n)
#Weighted regression is needed because each response is a mean of varying numbers of items

#as the response is an average of n equally variable observations then var(y) = sigma^2 / n
# = sigma^2 / w then w = n

summary(q5.weighted.lm)
#for a one week increase, the expected increase in mean weight is 1.0804g
summary(q5.weighted.lm)$sigma

#B//
q5.unweighted.lm <- lm(MeanWeight ~ Week)
anova(q5.weighted.lm)
anova(q5.unweighted.lm)
anova(q5.weighted.lm, q5.unweighted.lm)
summary(q5.unweighted.lm)$sigma
#RSS = 737.55
#SS(lof) = 294.60 df(lof) = 8
#RSS = SS(pe) + SS(lof)
SS.PE <- 737.55 - 294.60
#df(PE) = 41 - 8 = 33
#F <- (SS(lof) / df(lof)) / (ss(pe) / df(pe))
f <- (294.60 / 8) / (SS.PE / 33)
#f val of 2.7435
#critical value of F(0.05, 8, 33) is 2.234562 and pval 0.019391
#can reject hypothesis of no lack of fit
#there is a lack of fit

#C//
# RSS/( givenSigma ^ 2) ~ chi2(n - (p + 1)) ~ (43 - (1 + 1))
737.55/(6^2) #20.4875 compared to crit value of chisq
#critical value of chi(0.05, 41) is 56.94239 and p val of 0.99618
qchisq(0.05, 41, lower.tail = F)
#compare this to the 20.4875, as this is not higher than the test statistic it is alright
#cannot reject hypothesis of no lack of fit
#there is no lack of fit

'Question 6'
#A//
#Sex is a categorical variable and needs to be presented by a numeric variable in the model

#B//
#female becomes Y = 68.1533 - 6.7400X
#male becomes Y = 71.9933 - 7.6467X

male.babies.lm <- lm(Conc ~ Time , subset = Sex == 'M')
female.babies.lm <- lm(Conc ~ Time, subset = Sex == 'F')
plot(Time, Conc)
abline(male.babies.lm)
abline(female.babies.lm)
summary(male.babies.lm)
#intercept 71.9933
summary(female.babies.lm)
#intercept 68.1533

plot(injection.df$Time, injection.df$Conc, main = 'Same regression line for both sex', xlab = 'Time', ylab = 'Conc', pch = c('F', 'M'))
print(sort(injection.df[1,,], decreasing = T)) #just verifies that the max value is the right gender
legend(1, 20, pch = 'FM', legend = c('Female','Male'))
abline(injection.lm)

#C-E//
babies1.lm <- lm(Conc ~ Time)
summary(babies1.lm)

babies2.lm <- lm(Conc ~ Time + Sex)
model.matrix(babies2.lm)
summary(babies2.lm)

babies3.lm <- lm(Conc ~ Time + Sex + (Time * Sex))
summary(babies3.lm)
anova(babies3.lm)

babies4.lm <- lm(Conc ~ Sex)

#C
#h0: b2=0
summary(babies1.lm)
anova(babies1.lm)
summary(babies2.lm)
anova(babies2.lm)
anova(babies1.lm, babies2.lm)
#297 and 298 residuals
#RSS of 31569 and (31474 + 94.08) are not a significant difference

#conclude that the true regression lines have the same intercept

#D
#h0: b3 = 0
anova(babies3.lm, babies2.lm)
#p val of 0.2815
#conclude that they are paralell

#E//
#H0: b2 = b3 = 0
anova(babies1.lm, babies3.lm)
#p val of 0.3596
#conclude that the true regression lines have the same intercept

#F//
#the single coincident regression line
#we have concluded that the lines coincide

#G//
#There is a negative relationship betwen concentration and time
#as time increases concentration decreases
#the relationship is the same regardless of gender


