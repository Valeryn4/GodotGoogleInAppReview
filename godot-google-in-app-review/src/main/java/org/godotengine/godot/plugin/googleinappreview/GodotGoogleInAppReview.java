package org.godotengine.godot.plugin.googleinappreview;


import android.util.ArraySet;

import org.godotengine.godot.Dictionary;
import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.testing.FakeReviewManager;
import com.google.android.play.core.tasks.Task;

public class GodotGoogleInAppReview extends GodotPlugin {

    private ReviewManager reviewManager;
    private FakeReviewManager fakeManager;

    public GodotGoogleInAppReview(Godot godot) {
        super(godot);
        reviewManager = ReviewManagerFactory.create(getActivity());
        fakeManager = new FakeReviewManager(getGodot().getContext());

    }

    public void requestReview() {
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        requestReviewProcess(request, reviewManager);
    }


    public void requestFakeReview() {
        Task<ReviewInfo> request = fakeManager.requestReviewFlow();
        requestReviewProcess(request, fakeManager);
    }

    private void requestReviewProcess(Task<ReviewInfo> request, ReviewManager revManager) {
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Getting the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                Task <Void> flow = revManager.launchReviewFlow(getActivity(), reviewInfo);
                flow.addOnCompleteListener(taskDone -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown.
                    emitSignal("result",  true, "OK");
                });
            }
            else {
                emitSignal("result", false, "failed");
            }
        });
    }


    @NonNull
    @Override
    public String getPluginName() {
        return "GodotGoogleInAppReview";
    }

    @NonNull
    @Override
    public List<String> getPluginMethods() {
        return Arrays.asList("requestReview", "requestFakeReview");
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();

        signals.add(new SignalInfo("result", Boolean.class, String.class));

        return signals;
    }
}
