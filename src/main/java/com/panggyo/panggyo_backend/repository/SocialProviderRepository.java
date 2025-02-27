package com.panggyo.panggyo_backend.repository;

import com.panggyo.panggyo_backend.entity.SocialProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialProviderRepository extends JpaRepository<SocialProvider, Long> {
    SocialProvider findByProviderId(String providerId);
}
