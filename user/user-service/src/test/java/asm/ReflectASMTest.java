package asm;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import org.junit.Test;

public class ReflectASMTest
{
    @Test
    public void test()
    {
        MethodAccess.get(UserService.class);

        ConstructorAccess.get(UserService.class).newInstance();
    }
}
