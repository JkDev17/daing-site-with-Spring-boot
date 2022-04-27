package dating.dating.scheduler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import dating.dating.repositories.UserVisitedUsersRepository;

@Component
@EnableScheduling
public class SchedulingConfig 
{
    @Autowired
    UserVisitedUsersRepository userVisitedUsersRepository;

    @Scheduled(cron = "0 00 0 * 7 ?") // cron For Sunday 24:00 
    public void cleanUserVisitedUsersNLogsEverySunday() 
    {
        List<String> files;
        try (Stream<Path> paths = Files.walk(Paths.get("C:\\Users\\USER\\Documents\\Spring//dating")))
        {
            files = paths.map(Path::toString)
                    .filter(p -> p.endsWith(".gz") )
                    .collect(Collectors.toList());
            files.stream().forEach(System.out::println);
            for (String file: files)
            {
               Files.delete(Path.of(file));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        userVisitedUsersRepository.deleteAll();
    }
}