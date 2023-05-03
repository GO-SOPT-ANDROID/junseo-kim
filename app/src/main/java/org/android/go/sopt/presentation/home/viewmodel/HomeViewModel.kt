package org.android.go.sopt.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import org.android.go.sopt.R
import org.android.go.sopt.data.model.PartMember

class HomeViewModel : ViewModel() {
    private val partMemberList: List<PartMember> = listOf(
        PartMember(R.drawable.img_android, "Hello Go Android !"),
        PartMember(
            R.drawable.img_junseo, "김준서"
        ),
        PartMember(R.drawable.img_daehwan, "계대환"),
        PartMember(R.drawable.img_eujin, "곽의진"),
        PartMember(R.drawable.img_minjeong, "김민정"),
        PartMember(R.drawable.img_sangho, "김상호"),
        PartMember(R.drawable.img_seonhwan, "김선환"),
        PartMember(R.drawable.img_subin, "김수빈"),
        PartMember(R.drawable.img_jiyoung, "김지영"),
        PartMember(R.drawable.img_hajeong, "김하정"),
        PartMember(R.drawable.img_minju, "박민주"),
        PartMember(R.drawable.img_sohyun, "박소현"),
        PartMember(R.drawable.img_sumin, "배수민"),
        PartMember(R.drawable.img_jihyeon_bae, "배지현"),
        PartMember(R.drawable.img_hyeseon, "백혜선"),
        PartMember(R.drawable.img_jaewon, "서재원"),
        PartMember(R.drawable.img_seohyun, "신서현"),
        PartMember(R.drawable.img_jihyeon_ahn, "안지현"),
        PartMember(R.drawable.img_sangwook, "우상욱"),
        PartMember(R.drawable.img_jooyoung, "윤주영"),
        PartMember(R.drawable.img_gaeun, "이가은"),
        PartMember(R.drawable.img_nayeong, "이나영"),
        PartMember(R.drawable.img_daeun, "이다은"),
        PartMember(R.drawable.img_sak, "이삭"),
        PartMember(R.drawable.img_somin, "이소민"),
        PartMember(R.drawable.img_soohyeon, "이수현"),
        PartMember(R.drawable.img_junhee, "이준희"),
        PartMember(R.drawable.img_taehee, "이태희"),
        PartMember(R.drawable.img_haeun, "이하은"),
        PartMember(R.drawable.img_chaeyeon, "전채연"),
        PartMember(R.drawable.img_seona, "함선아"),
        PartMember(R.drawable.img_yeonjin, "황연진"),
    )

    fun getPartMemberList(): List<PartMember> = partMemberList
}