package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemberRepository {
    private Map<Long, Member> store = new HashMap();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    //생성자를 만들 경우 private로 만드는게 좋다

    public static MemberRepository getInstance() {
        return instance;
    }

    private MemberRepository() {

    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    //store에 있는 모든 값을 꺼내서 새로운 ArrayList에 넣어서 반환해줌
    //=>store에 있는 value list를 따로 건들고 싶지 않아서 (store 객체 보호)
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}
