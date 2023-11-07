package lotto.service;

import java.util.Arrays;
import java.util.List;
import lotto.io.read.InputReader;
import lotto.validation.LottoValidator;

public class LottoInputReader {

    private final InputReader reader;
    private final LottoOutputWriter writer;
    private final LottoValidator validator;

    public static LottoInputReader of(InputReader reader, LottoOutputWriter writer, LottoValidator validator) {
        return new LottoInputReader(reader, writer, validator);
    }

    private LottoInputReader(InputReader reader, LottoOutputWriter writer, LottoValidator validator) {
        this.writer = writer;
        this.reader = reader;
        this.validator = validator;
    }

    public int readPurchaseAmount() {
        writer.showPurchaseAmountInputMessage();

        while (true) {
            try {
                String purchaseAmountInput = reader.readLine();
                ensurePurchaseAmountIsValid(purchaseAmountInput);

                return Integer.parseInt(purchaseAmountInput);

            } catch (IllegalArgumentException ex) {
                writer.showExceptionMessage(ex.getMessage());
            }
        }
    }

    public List<Integer> readWinNumbers() {
        writer.showLottoWinNumbersInputMessage();

        while (true) {
            try {
                String winNumbersInput = reader.readLine();
                ensureWinNumbersIsValid(winNumbersInput);

                return Arrays.stream(winNumbersInput.split(","))
                        .map(it -> Integer.parseInt(it.trim()))
                        .sorted()
                        .toList();

            } catch (IllegalArgumentException ex) {
                writer.showExceptionMessage(ex.getMessage());
            }
        }
    }

    public int readBonusNumber(List<Integer> winNumbers) {
        writer.showLottoBonusNumberInputMessage();

        while (true) {
            try {
                String bonusNumberInput = reader.readLine();
                ensureBonusNumberIsValid(winNumbers, bonusNumberInput);

                return Integer.parseInt(bonusNumberInput);

            } catch (IllegalArgumentException ex) {
                writer.showExceptionMessage(ex.getMessage());
            }
        }
    }

    private void ensureBonusNumberIsValid(List<Integer> winNumbers, String bonusNumberInput) {
        validator.verifyBonusNumber(winNumbers, bonusNumberInput);
    }

    private void ensureWinNumbersIsValid(String winNumbersInput) {
        validator.verifyWinNumbers(winNumbersInput);
    }

    private void ensurePurchaseAmountIsValid(String purchaseAmountInput) {
        validator.verifyPurchaseAmount(purchaseAmountInput);
    }
}
