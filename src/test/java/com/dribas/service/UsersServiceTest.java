package com.dribas.service;

import com.dribas.ValidationException;
import com.dribas.dao.UsersStorage;
import com.dribas.entity.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UsersServiceTest {
    private static final String CORRECT_LOGIN = "qwe_123";
    private static final String CORRECT_PASS = "qwe_123";

    @Mock
    private UsersStorage storageMock;

    @InjectMocks
    private UsersService service;

    @Captor
    private ArgumentCaptor<User> argumentCaptor;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnFalse_whenNotDeleteUser() throws Exception {
        int idDoesNotExist = 1;
        when(storageMock.delete(idDoesNotExist)).thenReturn(false);
        boolean resultReturn = service.delete(idDoesNotExist);
        assertFalse(resultReturn);
        verify(storageMock).delete(idDoesNotExist);
    }

    @Test
    public void shouldSaveUser_whenNameIsCorrect() throws Exception {
        String correctName = "CorrectName";
        User user = new User(CORRECT_LOGIN, CORRECT_PASS, correctName, correctName);
        service.save(user);
        verify(storageMock).save(argumentCaptor.capture());
        assertEquals(user, argumentCaptor.getValue());
    }

    @Test
    public void shouldThrowException_whenNameIsTooShort() throws Exception {
        String shortName = "n";
        User user = new User(CORRECT_LOGIN, CORRECT_PASS, shortName, shortName);
        String expectedMessage = "User name is too short. " +
                "It must be longer than 3 character.";
        exception.expect(ValidationException.class);
        exception.expectMessage(expectedMessage);
        service.save(user);
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
        String expectedMessage = "User name is too long. " +
                "It must be shooter than 255 character.";
        exception.expect(ValidationException.class);
        exception.expectMessage(expectedMessage);
        service.save(user);
    }

    @Test
    public void shouldReturnUserById_whenItIsExist() throws Exception {
        int id = 1;
        User user = new User();
        when(storageMock.getById(id)).thenReturn(user);
        User result = service.getById(id);
        assertEquals(user, result);
    }

}