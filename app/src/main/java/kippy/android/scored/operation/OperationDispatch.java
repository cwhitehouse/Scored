package kippy.android.scored.operation;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by christianwhitehouse on 6/20/14.
 */
public class OperationDispatch {

	private static final int MAX_OPERATION_COUNT = 1;

	private LinkedList<MyOperation> mOperationQueue;
	private LinkedList<MyOperation> mRunningOperations;

	private static OperationDispatch sSelf;

	public static OperationDispatch getInstance() {
		if(sSelf == null)
			sSelf = new OperationDispatch();

		return sSelf;
	}

	private OperationDispatch() {
		mOperationQueue = new LinkedList<MyOperation>();
		mRunningOperations = new LinkedList<MyOperation>();
	}

	public void runOperation(MyOperation operation) {
		mOperationQueue.add(operation);
		checkOperationQueue();
	}

	public void onOperationFinished(MyOperation operation) {
		if(mRunningOperations.remove(operation))
			checkOperationQueue();
	}

	private void checkOperationQueue() {
		if(mRunningOperations.size() < MAX_OPERATION_COUNT && !mOperationQueue.isEmpty()) {
			MyOperation nextOperation = mOperationQueue.poll();
			mRunningOperations.add(nextOperation);
			nextOperation.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
	}

	public List<MyOperation> findOperations(Class operationClass) {
		List<MyOperation> foundOperations = new ArrayList<MyOperation>();

		for(MyOperation heyOperation :  mRunningOperations) {
			if(heyOperation.getClass() == operationClass)
				foundOperations.add(heyOperation);
		}

		for(MyOperation heyOperation : mOperationQueue) {
			if(heyOperation.getClass() == operationClass)
				foundOperations.add(heyOperation);
		}

		return foundOperations;
	}

}
