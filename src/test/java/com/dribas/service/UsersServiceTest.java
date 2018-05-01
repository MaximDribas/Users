package com.dribas.service;

import com.dribas.ValidationException;
import com.dribas.dao.UsersStorage;
import com.dribas.entity.User;
import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UsersServiceTest {
    private static final String CORRECT_LOGIN = "qwe_123";
    private static final String CORRECT_PASS = "qwe_123";


    @Mock
    private UsersStorage storageMock;

    @InjectMocks
    private UsersService serviceMock;

    @Captor
    private ArgumentCaptor<User> argumentCaptor;

    public UsersServiceTest (){}

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnFalse_whenNotDeleteUser() throws Exception {
        int idDoesNotExist = -1;

        when(storageMock.delete(idDoesNotExist)).thenReturn(false);

        boolean resultReturn= serviceMock.delete(idDoesNotExist);
        assertEquals(false,resultReturn);

        verify(storageMock).delete(idDoesNotExist);
    }

    @Test
    public void shouldSaveUser_whenNameIsCorrect() throws Exception {
        String correctName = "CorrectName";
        User user = new User(CORRECT_LOGIN, CORRECT_PASS, correctName, correctName);

        serviceMock.save(user);

        verify(storageMock).save(argumentCaptor.capture());
        assertEquals(user,argumentCaptor.getValue());
    }

    @Test
    public void shouldThrowException_whenNameIsTooShort() throws Exception {
        String shortName = "n";
        User user = new User(CORRECT_LOGIN, CORRECT_PASS, shortName, shortName);
        String expectedMessage="User name is too short. "+
                "It must be longer than 3 character.";

        try {
            serviceMock.save(user);
        } catch (ValidationException e){
            assertEquals(expectedMessage,e.getMessage());
        }
    }

    @Test
    public void shouldThrowException_whenNameIsTooLong() throws Exception {
        String longName = "Very looooooooooooooooooooooooooooooooooooooooo" +
                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "ooooooooooooooooooooooooooooong name";
        User user = new User(CORRECT_LOGIN, CORRECT_PASS, longName, longName);
        String expectedMessage="User name is too long. "+
                "It must be shooter than 255 character.";

        try {
            serviceMock.save(user);
        } catch (ValidationException e){
            assertEquals(expectedMessage,e.getMessage());
        }
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowException_whenUserNameIsTooShort() throws Exception {
        serviceMock.getById(-1);
    }

}