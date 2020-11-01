package photo.photoStudio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import photo.photoStudio.domain.Community;
import photo.photoStudio.repository.CommunityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;

    @Transactional
    public void saveCommunity(Community community){
        communityRepository.save(community);
    }

    @Transactional
    public void deleteCommunity(Long id){
        Community community = communityRepository.findOne(id);
        communityRepository.delete(community);
    }

    @Transactional
    public void updateCommunity(Long id, String title,String content){
        Community community = communityRepository.findOne(id);
        community.updateSetCommunity(title, content);
    }

    public List<Community> findAll(String type){
        return communityRepository.findAll(type);
    }

    public Community findOne(Long id){
        return communityRepository.findOne(id);
    }

    public List<Community> findPagingAll(String type, int startIndex, int pageSize){
        return communityRepository.findListPaging(type,startIndex,pageSize);
    }
}
