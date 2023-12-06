package utils;

import dto.UserDto;
import java.util.List;

import static utils.DataReader.getTestData;

public class ResponseUtils {

    public static boolean checkIfGetUserListFilterWorks(List<UserDto> userDtos){
        for (UserDto info : userDtos) {
            if (info.getUserRole().equals(getTestData("filter_role")) && info.getDbStatus().equals(getTestData("filter_status"))) {
                return true;
            }
        }
        return false;
    }
}
