package ru.mentorbank.backoffice.services.moneytransfer;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.mentorbank.backoffice.dao.OperationDao;
import ru.mentorbank.backoffice.dao.stub.OperationDaoStub;
import ru.mentorbank.backoffice.model.stoplist.JuridicalStopListRequest;
import ru.mentorbank.backoffice.model.stoplist.PhysicalStopListRequest;
import ru.mentorbank.backoffice.model.stoplist.StopListInfo;
import ru.mentorbank.backoffice.model.stoplist.StopListStatus;
import ru.mentorbank.backoffice.model.transfer.AccountInfo;
import ru.mentorbank.backoffice.model.transfer.JuridicalAccountInfo;
import ru.mentorbank.backoffice.model.transfer.PhysicalAccountInfo;
import ru.mentorbank.backoffice.model.transfer.TransferRequest;
import ru.mentorbank.backoffice.services.accounts.AccountService;
import ru.mentorbank.backoffice.services.accounts.AccountServiceBean;
import ru.mentorbank.backoffice.services.moneytransfer.exceptions.TransferException;
import ru.mentorbank.backoffice.services.stoplist.StopListService;
import ru.mentorbank.backoffice.services.stoplist.StopListServiceStub;
import ru.mentorbank.backoffice.test.AbstractSpringTest;

public class MoneyTransferServiceTest extends AbstractSpringTest {

	@Autowired
	private MoneyTransferServiceBean moneyTransferService;
	
	@Before
	public void setUp() {
	}

	@Test
	public void transfer() throws TransferException {
		//fail("not implemented yet");
		// TODO: Необходимо протестировать, что для хорошего перевода всё
		// работает и вызываются все необходимые методы сервисов
		// Далее следует закоментированная заготовка
		
		StopListService mockedStopListService = mock(StopListServiceStub.class);
		AccountService mockedAccountService = mock(AccountServiceBean.class);
		
		TransferRequest request = new TransferRequest();
		
		PhysicalAccountInfo srcPhysicalAccount = new PhysicalAccountInfo();
		JuridicalAccountInfo dstJuridicalAccount = new JuridicalAccountInfo();
		
		srcPhysicalAccount.setAccountNumber("1111111111111111");
		srcPhysicalAccount.setDocumentSeries(StopListServiceStub.SERIES_FOR_OK_STATUS);
		dstJuridicalAccount.setAccountNumber("2222222222222222");
		dstJuridicalAccount.setInn(StopListServiceStub.INN_FOR_OK_STATUS);
		request.setSrcAccount(srcPhysicalAccount);
		request.setDstAccount(dstJuridicalAccount);
		
		moneyTransferService.setStopListService(mockedStopListService);
		moneyTransferService.setAccountService(mockedAccountService);
		
		StopListInfo stopListInfoOK = new StopListInfo();
		stopListInfoOK.setStatus(StopListStatus.OK);
		when(mockedStopListService.getJuridicalStopListInfo(any(JuridicalStopListRequest.class)))
		.thenReturn(stopListInfoOK);
		when(mockedStopListService.getPhysicalStopListInfo(any(PhysicalStopListRequest.class)))
		.thenReturn(stopListInfoOK);
		when(mockedAccountService.verifyBalance(any(AccountInfo.class))).thenReturn(true);
		
		moneyTransferService.transfer(request);
		
		verify(mockedStopListService).getJuridicalStopListInfo(any(JuridicalStopListRequest.class));
		verify(mockedStopListService).getPhysicalStopListInfo(any(PhysicalStopListRequest.class));
		verify(mockedAccountService).verifyBalance(srcPhysicalAccount);
	}
}
