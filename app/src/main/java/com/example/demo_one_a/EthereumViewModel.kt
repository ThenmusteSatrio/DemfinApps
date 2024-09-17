package com.example.demo_one_a

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.metamask.androidsdk.Dapp
import io.metamask.androidsdk.Ethereum
import io.metamask.androidsdk.EthereumRequest
import io.metamask.androidsdk.EthereumState
import javax.inject.Inject

@HiltViewModel
class EthereumViewModel @Inject constructor(
    private val ethereum: Ethereum
): ViewModel() {

    val ethereumState = MediatorLiveData<EthereumState>().apply {
        addSource(ethereum.ethereumState) { newEthereumState ->
            value = newEthereumState
        }
    }

    // Wrapper function to connect the dapp.
    fun connect(dapp: Dapp, callback: ((Any?) -> Unit)?) {
        ethereum.connect(dapp, callback)
    }

    // Wrapper function call all RPC methods.
    fun sendRequest(request: EthereumRequest, callback: ((Any?) -> Unit)?) {
        ethereum.sendRequest(request, callback)
    }
}