package org.android.go.sopt.util

object PublicString {
    // Intent Keys
    const val USER_NAME = "USER_NAME"
    const val USER_SKILL = "USER_SKILL"

    // Server Response String
    const val CONNECTION_FAIL = "서버와 연결에 실패했습니다."
    const val UNEXPECTED_ERROR = "예기치 못한 오류가 발생하였습니다."
    const val SERVER_COMMUNICATION_SUCCESS = "서버통신에 성공하였습니다."

    //REGEX
    const val REGEX_ID = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{6,10}\$"
    const val REGEX_PW = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{6,12}\$"

    //Throw Error Messages
    const val ID_IS_NULL = "id is null"
    const val PW_IS_NULL = "pw is null"
    const val NAME_IS_NULL = "name is null"
    const val SKILL_IS_NULL = "skill is null"

}