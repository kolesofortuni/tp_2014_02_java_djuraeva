package frontend;

import message.AddressService;
import message.Msg.MessageService;
import org.apache.commons.lang.ObjectUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.mockito.internal.matchers.Null;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import static org.mockito.Mockito.*;

/*
 * Created by elvira on 06.03.14.
 */
public class doGetTest {
    final private HttpServletRequest request = mock(HttpServletRequest.class);
    final private HttpServletResponse response = mock(HttpServletResponse.class);
    final private HttpSession session = mock(HttpSession.class);
    private StringWriter stringWrite = new StringWriter();
    private PrintWriter writer = new PrintWriter(stringWrite);

    Frontendl frontend;
    private static String url;
    private static String correctAnswer;
    private final String login = AccountServiceTest.getRandString(5);

    @Before
    public void setUp() throws IOException{
        Long userId = 5L;
        String sessionID = "session";
        MessageService msg = new MessageService();
        frontend = new Frontendl(msg);
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn(sessionID);
        when(request.getPathInfo()).thenReturn(url);
        frontend.setSessionAuthStatus(sessionID, new UserSession(sessionID, login, mock(AddressService.class)));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(userId);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void testDoGet() throws Exception {
        frontend.doGet(request, response);
        boolean isCorrectAnswer = (stringWrite.toString().contains(correctAnswer));
        Assert.assertTrue(isCorrectAnswer);
    }

    public static Boolean goTest(String _url, String _correctAnswer) {
        JUnitCore core = new JUnitCore();
        url = _url;
        correctAnswer = _correctAnswer;
        return core.run(doGetTest.class).wasSuccessful();
    }
}
