package com.bookfit.www.backend.dto

data class KakaoAuthDTO(
    val access_token: String,
    val token_type: String,
    val refresh_token: String,
    val id_token: String?,
    val expires_in: Long,
    val scope: String?,
    val refresh_token_expires_in: Long
)


data class KakaoUserDTO(
    val id: Long,
    val connected_at: String,
    val kakao_account: KakaoAccount,
    val properties: Map<String, String>?,  // 커스텀 속성은 키-값 쌍으로 전달됨
    val for_partner: ForPartner? = null
)

data class KakaoAccount(
    val profile_nickname_needs_agreement: Boolean,
    val profile_image_needs_agreement: Boolean,
    val profile: KakaoProfile?,

    val name_needs_agreement: Boolean,
    val nickname: String?,

    val email_needs_agreement: Boolean,
    val is_email_valid: Boolean?,
    val is_email_verified: Boolean?,
    val email: String?,

    val age_range_needs_agreement: Boolean,
    val age_range: String?,

    val birthyear_needs_agreement: Boolean,
    val birthyear: String?,

    val birthday_needs_agreement: Boolean,
    val birthday: String?,
    val birthday_type: String?,
    val is_leap_month: Boolean?,

    val gender_needs_agreement: Boolean,
    val gender: String?,

    val phone_number_needs_agreement: Boolean,
    val phone_number: String?,

    val ci_needs_agreement: Boolean,
    val ci: String?,
    val ci_authenticated_at: String?
)

data class KakaoProfile(
    val nickname: String?,
    val thumbnail_image_url: String?,
    val profile_image_url: String?,
    val is_default_image: Boolean?,
    val is_default_nickname: Boolean?
)

data class ForPartner(
    val uuid: String?
)

