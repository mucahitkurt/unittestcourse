package io.mucahit.course.unittest.mockito;

import io.mucahit.course.unittest.courserecord.model.Student;
import io.mucahit.course.unittest.courserecord.model.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * @author mucahitkurt
 * @since 1.05.2018
 */
@ExtendWith(MockitoExtension.class)
public class TimeoutVerificationModeDemo {

    @Mock
    private StudentRepository studentRepository;

    @Test
    void readStudent() {
        when(studentRepository.findById("id1")).thenReturn(Optional.of(new Student("", "", "")));

        new Thread(() -> {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            studentRepository.findById("id1");
        }).start();

        verify(studentRepository, timeout(100).times(1)).findById("id1");

    }

}
