package kippy.android.scored.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import kippy.android.scored.R;
import kippy.android.scored.fragment.MyFragment;

/**
 * Created by christianwhitehouse on 6/20/14.
 */
public abstract class MyActivity<F extends MyFragment> extends BaseActivity {

	//================================================================================
	// Constants
	//================================================================================

	private static final String MY_FRAGMENT_TAG = "hey_fragment";

	//================================================================================
	// Class Variables
	//================================================================================

	private View vBase;

	private View vStatusBarOverlay;
	private View vNavigationBarOverlay;

	private View vNetworkingOverlay;

	private View vLoadingSpinner;

	private View vError;
	private TextView vErrorTitle;
	private TextView vErrorMessage;

	private F vFragment;

	//================================================================================
	// Life Cycle Management
	//================================================================================

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		vBase = findViewById(R.id.my_base);
		vStatusBarOverlay = findViewById(R.id.my_status_bar_overlay);
		vNavigationBarOverlay = findViewById(R.id.my_nav_bar_overlay);

		vNetworkingOverlay = findViewById(R.id.my_networking);

		vLoadingSpinner = findViewById(R.id.hey_loading_spinner);

		vError = findViewById(R.id.hey_error);
		vErrorTitle = (TextView) findViewById(R.id.hey_error_title);
		vErrorMessage = (TextView) findViewById(R.id.hey_error_message);

		findViewById(R.id.hey_error_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				hideError(true);
			}
		});

		FragmentManager fragmentManager = getFragmentManager();
		vFragment = (F) fragmentManager.findFragmentByTag(MY_FRAGMENT_TAG);
		if(vFragment == null) {
			vFragment = createFragment();
			if(vFragment != null)
				getFragmentManager().beginTransaction().add(R.id.my_content, vFragment, MY_FRAGMENT_TAG).commit();
		}
	}

	@Override
	public void onBackPressed() {
		if(vFragment == null || !vFragment.onBackPressed())
			super.onBackPressed();
	}

	//================================================================================
	// Implemented Methods
	//================================================================================

	@Override
	public int getLayoutID() {
		return R.layout.my_activity;
	}

	//================================================================================
	// Methods to Implement
	//================================================================================

	public abstract F createFragment();

	//================================================================================
	// System Bar Management
	//================================================================================

	public View getBaseView() {
		return vBase;
	}

	public View getStatusBarOverlay() {
		return vStatusBarOverlay;
	}

	public View getvNavigationBarOverlay() {
		return vNavigationBarOverlay;
	}

	//================================================================================
	// Loading/Error Management
	//================================================================================

	public void cancelAnimations() {
		vNetworkingOverlay.animate().setListener(null).cancel();
		vError.animate().setListener(null).cancel();
		vLoadingSpinner.animate().setListener(null).cancel();
	}

	public void showLoading(boolean animate) {
		cancelAnimations();

		if(vNetworkingOverlay.getVisibility() != View.VISIBLE) {
			vError.setVisibility(View.GONE);

			vLoadingSpinner.setVisibility(View.VISIBLE);
			vLoadingSpinner.setAlpha(1f);

			vNetworkingOverlay.setVisibility(View.VISIBLE);
			if(animate) {
				vNetworkingOverlay.setAlpha(0f);
				vNetworkingOverlay.animate().alpha(1f).setListener(null);
			} else
				vNetworkingOverlay.setAlpha(1f);

		} else if(vLoadingSpinner.getVisibility() != View.VISIBLE) {
			vNetworkingOverlay.setVisibility(View.VISIBLE);
			vNetworkingOverlay.setAlpha(1f);

			vError.animate().alpha(0f).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					vError.setVisibility(View.GONE);

					vLoadingSpinner.setAlpha(0f);
					vLoadingSpinner.setVisibility(View.VISIBLE);
					vLoadingSpinner.animate().alpha(1f).setListener(null);
				}
			});
		}
	}

	public void hideLoading(boolean animate) {
		cancelAnimations();

		if(vNetworkingOverlay.getVisibility() != View.VISIBLE) return;

		if(animate) {
			vNetworkingOverlay.setAlpha(1f);
			vNetworkingOverlay.animate().alpha(0f).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					vNetworkingOverlay.setVisibility(View.GONE);
					vNetworkingOverlay.setAlpha(1f);
					vError.setVisibility(View.GONE);
					vError.setAlpha(1f);
					vLoadingSpinner.setVisibility(View.GONE);
					vLoadingSpinner.setAlpha(1f);
				}
			});
		} else {
			vNetworkingOverlay.setVisibility(View.GONE);
			vNetworkingOverlay.setAlpha(1f);
			vError.setVisibility(View.GONE);
			vError.setAlpha(1f);
			vLoadingSpinner.setVisibility(View.GONE);
			vLoadingSpinner.setAlpha(1f);
		}
	}

	public void showError(String errorTitle, String errorMessage, boolean animate) {
		cancelAnimations();

		vErrorTitle.setText(errorTitle);
		vErrorMessage.setText(errorMessage);

		if(vNetworkingOverlay.getVisibility() != View.VISIBLE) {
			vLoadingSpinner.setVisibility(View.GONE);

			vError.setVisibility(View.VISIBLE);
			vError.setAlpha(1f);

			vNetworkingOverlay.setVisibility(View.VISIBLE);
			if(animate) {
				vNetworkingOverlay.setAlpha(0f);
				vNetworkingOverlay.animate().alpha(1f).setListener(null);
			} else
				vNetworkingOverlay.setAlpha(1f);

		} else if(vError.getVisibility() != View.VISIBLE) {
			vNetworkingOverlay.setVisibility(View.VISIBLE);
			vNetworkingOverlay.setAlpha(1f);

			vLoadingSpinner.animate().alpha(0f).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					vLoadingSpinner.setVisibility(View.GONE);
					vError.setAlpha(0f);
					vError.setVisibility(View.VISIBLE);
					vError.animate().alpha(1f).setListener(null);
				}
			});
		}
	}

	public void hideError(boolean animate) {
		hideLoading(animate);
	}

}
