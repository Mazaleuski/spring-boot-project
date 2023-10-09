package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.util.StopWatch;

@AllArgsConstructor
public class StatisticService {

    private final UserRepository userRepository;

    public double getTime() {
        StopWatch stopWatch = new StopWatch("App");
        stopWatch.start("App Startup");
        userRepository.read();
        stopWatch.stop();
        return stopWatch.getTotalTimeSeconds();
    }
}